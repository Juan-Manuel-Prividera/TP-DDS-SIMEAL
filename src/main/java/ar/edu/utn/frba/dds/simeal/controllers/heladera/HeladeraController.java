package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HeladeraController {
  private final Repositorio repositorio;
  private final SensorRepository sensorRepository;
  private final VisitaTecnicaRepository visitaRepository;
  private ViandaRepository viandaRepository;
  public HeladeraController(Repositorio repositorio, SensorRepository sensorRepository,
                            VisitaTecnicaRepository visitaRepository, ViandaRepository viandaRepository) {
    this.repositorio = repositorio;
    this.sensorRepository = sensorRepository;
    this.visitaRepository = visitaRepository;
    this.viandaRepository = viandaRepository;
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

  public Heladera create(Context ctx,String nombre, ModeloHeladera modelo, Ubicacion ubicacion) {
    ConfigReader configReader = new ConfigReader();
    Long colabId = ctx.sessionAttribute("colaborador_id");
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(colabId, Colaborador.class);
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<Heladera> heladerasCercanas = new ArrayList<>();
    for (Heladera heladera : heladeras) {
      if (heladera.getUbicacion().estaCercaDe(ubicacion,Integer.parseInt(configReader.getProperty("cond.cercania"))))
        heladerasCercanas.add(heladera);
    }

    Heladera heladera = new Heladera(nombre,ubicacion, LocalDate.now(),colaborador,modelo,true,heladerasCercanas);
    Sensor sensor = new Sensor(heladera,null);
    repositorio.guardar(heladera);
    repositorio.guardar(sensor);

    return heladera;
  }


  // GET /heladeras
  public void getAll(Context app) {
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<HeladeraDTO> heladerasDTOS = new ArrayList<>();
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(app.sessionAttribute("colaborador_id"), Colaborador.class);

    for (Heladera heladera : heladeras) {
      Sensor sensor = sensorRepository.buscarPorHeladera(heladera.getId());
      MedicionTemperatura ultimaMedicion = sensor.getUltimaTemperaturaRegistrada();
      int cantViandas = viandaRepository.buscarPorHeladera(heladera).size();
      if(ultimaMedicion == null) {
        heladerasDTOS.add(new HeladeraDTO(heladera, 0d,colaborador,cantViandas));
      } else {
        heladerasDTOS.add(new HeladeraDTO(heladera, ultimaMedicion.getTemperaturaMedida(),colaborador,cantViandas));
      }
    }

    app.json(heladerasDTOS);
  }

  public void getEstadoHeladera(Context ctx) {
    Logger.info("Entra a getEstadoHeladera");
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Estado Heladera");

    setNavBar(model,ctx);
    invalidarCacheNavegador(ctx);

    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    if (ctx.sessionAttribute("user_type").equals("JURIDICO") &&
      heladera.getColaboradorACargo() != null &&
      heladera.getColaboradorACargo().getId().equals(colaborador.getId()) ||
      ctx.sessionAttribute("user_type").equals("HUMANO")){

      model.put("puedeVerOpciones", true);
    }


    MedicionTemperatura ultimaMedicion = sensorRepository.buscarPorHeladera(heladera.getId()).getUltimaTemperaturaRegistrada();
    int cantViandas = viandaRepository.buscarPorHeladera(heladera).size();
    HeladeraDTO heladeraDTO;
    if (ultimaMedicion != null) {
      heladeraDTO = new HeladeraDTO(heladera, ultimaMedicion.getTemperaturaMedida(),colaborador,cantViandas);
    } else {
      heladeraDTO = new HeladeraDTO(heladera, 0D, colaborador,cantViandas);
    }
    model.put("heladera", heladeraDTO);

    Logger.info("Antes de hacer el Render, el map tiene: " + model);
    ctx.render("/heladeras/status_heladera.hbs",model);
  }

  public void indexReporteFallo(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);

    int cantViandas = viandaRepository.buscarPorHeladera(heladera).size();
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    setNavBar(model,ctx);
    invalidarCacheNavegador(ctx);
    model.put("titulo", "Reportar Fallo");
    HeladeraDTO heladeraDTO = new HeladeraDTO(heladera, 0D,colaborador,cantViandas);
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
      .imagen(saveImage(ctx))
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
    invalidarCacheNavegador(ctx);
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
  }

  private String saveImage(Context ctx){
    UploadedFile uploadedFile = ctx.uploadedFile("imagen");


    if (uploadedFile == null) {
      Logger.error("No se recibió ningún archivo");
      return null;
    }

    // Validar el tipo de archivo (opcional)
//    if (!uploadedFile.contentType().startsWith("image/")) {
//      Logger.error("El archivo no es una imagen válida");
//      return null;
//    }

    // Crear el directorio de destino si no existe
    String uploadDir = "src/main/resources/static/img/fallas/";
    File directory = new File(uploadDir);
    if (!directory.exists()) {
      directory.mkdirs();
    }


    // Guardar el archivo
    File file = new File(uploadDir + uploadedFile.filename());
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(uploadedFile.content().readAllBytes());
    } catch (IOException e) {
      Logger.error("Error al guardar el archivo - %s", e);
      return null;
    }

    String path = "/img/fallas/" + uploadedFile.filename();
    Logger.info("Se guardo el archivo correctamente");
    return path;
  }

  private void invalidarCacheNavegador(Context app) {
    app.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    app.header("Pragma", "no-cache");
    app.header("Expires", "0");
  }


}
