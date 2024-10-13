package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.*;

public class HeladeraController {
  private Repositorio repositorio;
  private Logger log;
  public HeladeraController(Repositorio repositorio) {
    this.repositorio = repositorio;
    log = Logger.getInstance("heladeraController");
  }

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model);
    model.put("titulo", "Home Heladera");

    app.render("/heladeras/home_heladera.hbs",model);
  }

  public void getAll(Context app) {
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<HeladeraDTO> heladerasDTOS = new ArrayList<>();

    for (Heladera heladera : heladeras) {
      heladerasDTOS.add(new HeladeraDTO(heladera, 0D));
    }

    app.json(heladerasDTOS);
  }

  public void getEstadoHeladera(Context ctx) {
    log.log(LoggerType.INFORMATION, "Entra a getEstadoHeladera");
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Estado Heladera");

    setNavBar(model);

    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    List<MedicionTemperatura> mediciones = (List<MedicionTemperatura>) repositorio
      .obtenerTodos(MedicionTemperatura.class);

    MedicionTemperatura ultimaMedicion = mediciones.stream()
      .max(Comparator.comparing(MedicionTemperatura::getFechaHora))
      .orElse(null);

    HeladeraDTO heladeraDTO;
    if (ultimaMedicion != null) {
      heladeraDTO = new HeladeraDTO(heladera, ultimaMedicion.getTemperaturaMedida());
    } else {
      heladeraDTO = new HeladeraDTO(heladera, 0D);
    }
    model.put("heladera", heladeraDTO);

    log.log(LoggerType.INFORMATION, "Antes de hacer el Render, el map tiene: " + model);
    ctx.render("/heladeras/status_heladera.hbs",model);
  }

  public void indexReporteFallo(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    setNavBar(model);
    model.put("titulo", "Reportar Fallo");
    HeladeraDTO heladeraDTO = new HeladeraDTO(heladera, 0D);
    model.put("heladera", heladeraDTO);

    ctx.render("/heladeras/reportar_fallo.hbs", model);
  }

  public void reportarFallo(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model);
    model.put("titulo", "Agreadecimiento Reporte");
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    // TODO: SACAR DE LA SESION
    Colaborador colaborador = (Colaborador)  repositorio
      .buscarPorId(1L, Colaborador.class);

    FallaTecnica fallaTecnica = FallaTecnica.builder()
      .heladera(heladera)
      .descripcion(ctx.formParam("descripcion"))
      .fechaHora(LocalDateTime.now())
      .colaborador(colaborador)
      .imagen(ctx.formParam("imagen"))
      .build();
    ServiceLocator.getController(IncidenteController.class).create(fallaTecnica);
    ctx.render("/heladeras/agradecimiento_reporte.hbs", model);
  }


  private void setNavBar(HashMap<String,Object> model) {
    // TODO: Hacer un if con el rol de la sesion
    model.put("esHumano", "true");
    // TODO: Esto esta en una cookie
    model.put("user_type","humano");

    model.put("heladeras", "seleccionado");
    model.put("username", "sacar de la sesion :)");
  }
}
