package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.dtos.IncidenteDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncidenteController {
  Repositorio repositorio;
  IncidenteRepository incidenteRepository;

  public IncidenteController(Repositorio repositorio, IncidenteRepository incidenteRepository) {
    this.repositorio = repositorio;
    this.incidenteRepository = incidenteRepository;
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
    repositorio.guardar((Persistente) incidente);
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
}
