package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.controllers.tecnico.EncargoController;
import ar.edu.utn.frba.dds.simeal.models.dtos.IncidenteDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncidenteController {
  private final Repositorio repositorio;
  private final IncidenteRepository incidenteRepository;
  private final EncargoController encargoController;

  public IncidenteController(Repositorio repositorio, IncidenteRepository incidenteRepository, EncargoController encargoController) {
    this.repositorio = repositorio;
    this.incidenteRepository = incidenteRepository;
    this.encargoController = encargoController;
  }

  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    List<Incidente> incidentes = incidenteRepository
      .buscarPorHeladera(Long.valueOf(ctx.pathParam("heladera_id")));
    List<IncidenteDTO> incidenteDTOS = new ArrayList<>();
    for (Incidente incidente : incidentes) {
      if (incidente.getClass().getName().equals(Alerta.class.getName())) {
        incidenteDTOS.add(new IncidenteDTO((Alerta) incidente));
      } else if (incidente.getClass().getName().equals(FallaTecnica.class.getName())) {
        incidenteDTOS.add(new IncidenteDTO((FallaTecnica) incidente));
      }
    }
    model.put("incidentes", incidenteDTOS);
    model.put("titulo", "Incidentes");
    if (!incidenteDTOS.isEmpty()) {
      model.put("hayIncidentes", true);
    }

    setNavBar(model, ctx);
    ctx.render("/heladeras/alertas_recientes.hbs", model);
  }

  public void create(Incidente incidente) {
    repositorio.guardar(incidente);

    // Avisar a tecnico mas cercano
    Tecnico tecnicoMasCercano = obtenerTecnicoMasCercano(incidente);
    if (tecnicoMasCercano != null) {
      Mensaje mensaje = new Mensaje(
        incidente.getNotificacion(),
        "Aviso de Incidente en " + incidente.getHeladera().getUbicacion().getStringUbi() +
          "\n Podra ver el detalle del aviso en el apartado de encargos e informar si lo acepta o rechaza.");
      Notificador.notificar(tecnicoMasCercano,mensaje);
      encargoController.create(incidente,tecnicoMasCercano);
    } else
      Logger.error("No hay tecnico a quien asignar el encargo");
  }

  private void setNavBar(HashMap<String,Object> model, Context app) {
    if (app.sessionAttribute("user_type").equals("HUMANO"))
      model.put("esHumano", "true");
    else if (app.sessionAttribute("user_type").equals("JURIDICO"))
      model.put("esJuridico", "true");

    model.put("user_type",app.sessionAttribute("user_type"));

    model.put("heladeras", "seleccionado");
    model.put("username", app.sessionAttribute("user_name"));
  }

  private Tecnico obtenerTecnicoMasCercano(Incidente incidente) {
    List<Tecnico> tecnicos = (List<Tecnico>) repositorio.obtenerTodos(Tecnico.class);
    Ubicacion ubiIncidente = incidente.getHeladera().getUbicacion();
    Tecnico tecnicoMasCercano = null;
    for (Tecnico tecnico : tecnicos) {
      if (tecnico.getAreaDeCobertura().cubreEstaUbicacion(ubiIncidente)) {
        Ubicacion ubiTecnico = tecnico.getAreaDeCobertura().getUbicacion();
        if (tecnicoMasCercano == null ||
          ubiTecnico.distanciaA(ubiIncidente) < tecnicoMasCercano.getAreaDeCobertura().getUbicacion().distanciaA(ubiIncidente))
          tecnicoMasCercano = tecnico;
      }
    }
    return tecnicoMasCercano;
  }
}
