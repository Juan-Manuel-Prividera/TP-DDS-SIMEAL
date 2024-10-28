package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LogType;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.*;

public class HeladeraController {
  private Repositorio repositorio;
  public HeladeraController(Repositorio repositorio) {
    this.repositorio = repositorio;
  }

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,app);
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
    Logger.info("Entra a getEstadoHeladera");
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Estado Heladera");

    setNavBar(model,ctx);

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

    Logger.info("Antes de hacer el Render, el map tiene: " + model);
    ctx.render("/heladeras/status_heladera.hbs",model);
  }

  public void indexReporteFallo(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    setNavBar(model,ctx);
    model.put("titulo", "Reportar Fallo");
    HeladeraDTO heladeraDTO = new HeladeraDTO(heladera, 0D);
    model.put("heladera", heladeraDTO);

    ctx.render("/heladeras/reportar_fallo.hbs", model);
  }

  public void reportarFallo(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model, ctx);
    model.put("titulo", "Agreadecimiento Reporte");
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    Colaborador colaborador = (Colaborador)  repositorio
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);

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


  private void setNavBar(HashMap<String,Object> model, Context app) {
    if (app.sessionAttribute("user_type").equals("HUMANO"))
      model.put("esHumano", "true");
    else if (app.sessionAttribute("user_type").equals("JURIDICO"))
      model.put("esJuridico", "true");

    model.put("user_type",app.sessionAttribute("user_type").toString().toLowerCase());

    model.put("heladeras", "seleccionado");
    model.put("username", app.sessionAttribute("user_name"));
    model.put("colaborador_id", app.sessionAttribute("colaborador_id"));
  }
}
