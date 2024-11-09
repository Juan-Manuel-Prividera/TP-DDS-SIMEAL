package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.*;

public class HeladeraController {
  private Repositorio repositorio;
  private SensorRepository sensorRepository;
  private VisitaTecnicaRepository visitaRepository;

  public HeladeraController(Repositorio repositorio, SensorRepository sensorRepository, VisitaTecnicaRepository visitaRepository) {
    this.repositorio = repositorio;
    this.sensorRepository = sensorRepository;
    this.visitaRepository = visitaRepository;
  }

  // GET /heladera
  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,app);
    model.put("titulo", "Home Heladera");
    String failed = app.queryParam("failed");
    String action = app.queryParam("action");
    if (failed != null && failed.equals("true") && action.equals("visitas")) {
      model.put("popup_title", "Acceso denegado");
      model.put("popup_message", "No tiene persmiso para acceder a las visitas tecnicas");
      model.put("popup_ruta", "/heladera");
    }

    app.render("/heladeras/home_heladera.hbs",model);
  }
  // GET /heladeras
  public void getAll(Context app) {
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<HeladeraDTO> heladerasDTOS = new ArrayList<>();
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(app.sessionAttribute("colaborador_id"), Colaborador.class);

    // TODO: PROBAR ESTO CUANDO TENGAMSO EL BROKER OK
    for (Heladera heladera : heladeras) {
      List<Sensor> sensores= sensorRepository.buscarPorHeladera(heladera.getId());
      MedicionTemperatura ultimaMedicion = sensores.get(0).getUltimaTemperaturaRegistrada();
      if(ultimaMedicion == null) {
        heladerasDTOS.add(new HeladeraDTO(heladera, 0D,colaborador));
      } else {
        heladerasDTOS.add(new HeladeraDTO(heladera, ultimaMedicion.getTemperaturaMedida(),colaborador));
      }
    }

    app.json(heladerasDTOS);
  }

  public void getEstadoHeladera(Context ctx) {
    Logger.info("Entra a getEstadoHeladera");
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Estado Heladera");

    setNavBar(model,ctx);

    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    if (ctx.sessionAttribute("user_type").equals("JURIDICO") &&
      (heladera.getColaboradorACargo() == null || !heladera.getColaboradorACargo().getId().equals(colaborador.getId())) ||
        ctx.sessionAttribute("user_type").equals("HUMANO")){
      model.put("puedeVerOpciones", "");
    } else {
      model.put("puedeVerOpciones", "true");
    }


    MedicionTemperatura ultimaMedicion = sensorRepository.buscarPorHeladera(heladera.getId()).get(0).getUltimaTemperaturaRegistrada();

    HeladeraDTO heladeraDTO;
    if (ultimaMedicion != null) {
      heladeraDTO = new HeladeraDTO(heladera, ultimaMedicion.getTemperaturaMedida(),colaborador);
    } else {
      heladeraDTO = new HeladeraDTO(heladera, 0D, colaborador);
    }
    model.put("heladera", heladeraDTO);

    Logger.info("Antes de hacer el Render, el map tiene: " + model);
    ctx.render("/heladeras/status_heladera.hbs",model);
  }

  public void indexReporteFallo(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    setNavBar(model,ctx);
    model.put("titulo", "Reportar Fallo");
    HeladeraDTO heladeraDTO = new HeladeraDTO(heladera, 0D,colaborador);
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

    heladera.desactivar();
    repositorio.actualizar(heladera);

    ServiceLocator.getController(IncidenteController.class).create(fallaTecnica);

    model.put("popup_title","Fallo Reportado");
    model.put("popup_message", "Gracias por reportar el fallo!");

    ctx.render("/heladeras/reportar_fallo.hbs", model);
  }

  // GET /heladera/visitas/{heladera_id}
  public void getVisitas(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model,ctx);
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    Heladera heladera = (Heladera) repositorio.buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")),Heladera.class);
    if (heladera.getColaboradorACargo() != null && Objects.equals(heladera.getColaboradorACargo().getId(), colaborador.getId())) {
      List<VisitaTecnica> vistas = visitaRepository.getPorHeladera(heladera.getId());
      List<VisitaTecnicaDTO> visitasDTO = new ArrayList<>();
      for (VisitaTecnica visita : vistas) {
        visitasDTO.add(new VisitaTecnicaDTO(visita));
      }
      model.put("visitas", visitasDTO);
      ctx.render("/heladeras/visitas.hbs", model);
    } else {
      ctx.redirect("/heladera?failed=true&action=visitas");
    }
  }

  private void setNavBar(HashMap<String,Object> model, Context app) {
    if (app.sessionAttribute("user_type").equals("HUMANO"))
      model.put("esHumano", "true");
    else if (app.sessionAttribute("user_type").equals("JURIDICO")) {
      model.put("esJuridico", "true");
      model.put("mostrarHeladerasJur", "true");
    }
    model.put("user_type",app.sessionAttribute("user_type").toString().toLowerCase());

    model.put("heladeras", "seleccionado");
    model.put("username", app.sessionAttribute("user_name"));
    model.put("colaborador_id", app.sessionAttribute("colaborador_id"));
    invalidarCacheNavegador(app);
  }

  private void invalidarCacheNavegador(Context app) {
    app.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    app.header("Pragma", "no-cache");
    app.header("Expires", "0");
  }


}
