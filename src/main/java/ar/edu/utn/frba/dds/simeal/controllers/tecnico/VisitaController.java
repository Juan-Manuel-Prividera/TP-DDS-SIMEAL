package ar.edu.utn.frba.dds.simeal.controllers.tecnico;

import ar.edu.utn.frba.dds.simeal.models.dtos.EncargoTecnicoDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class VisitaController {
  private final VisitaTecnicaRepository visitaTecnicaRepository;
  private final EncargoTecnicoRepostiry encargoTecnicoRepostiry;
  private final Repositorio repositorio;

  public VisitaController(VisitaTecnicaRepository visitaTecnicaRepository, EncargoTecnicoRepostiry encargoTecnicoRepostiry, Repositorio repositorio)  {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
    this.encargoTecnicoRepostiry = encargoTecnicoRepostiry;
    this.repositorio = repositorio;
  }

  // Este index es como el home del tecnico asi que tambien tiene los encargos
  // Podria verse de acomodar de otra forma las cosas pero de momento queda
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    Boolean failed = Boolean.parseBoolean(ctx.queryParam("failed"));
    String action = ctx.queryParam("action");

    setModel(model, ctx);
    setVisitasEncargos(model,ctx);
    invalidarCacheNavegador(ctx);

    if (failed && action.equals("RegistrarVisita")) {
      model.put("popup_title", "Error al registrar visita");
      model.put("popup_message","No puede registrar una visita si no acepto el encargo!!");
      model.put("popup_ruta","/tecnico/home");
      ctx.render("tecnico/tecnico_home.hbs",model);

    } else if (failed && action != null) {
      model.put("popup_title","Error al " + action + " encargo");
      model.put("popup_message","No se puede "+ action +" porque ya lo estaba!!");
      model.put("popup_ruta","/tecnico/home");
      ctx.render("tecnico/tecnico_home.hbs",model);

    } else if (!failed && action == null) {
      model.put("popup_ruta","/tecnico/home");
      ctx.render("tecnico/tecnico_home.hbs",model);

    } else if (!failed && action != null) {
      model.put("popup_title","Operación realizada exitosamente");
      model.put("popup_ruta","/tecnico/home");
      ctx.render("tecnico/tecnico_home.hbs",model);
    }

  }

  // GET /{encargo_id}/visita
  public void indexRegistroVisita(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    EncargoTecnico encargo = (EncargoTecnico) encargoTecnicoRepostiry
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);
    invalidarCacheNavegador(ctx);
    setModel(model, ctx);
    setVisitasEncargos(model,ctx);

    if (!encargo.getAceptado()) {
      ctx.redirect("/tecnico/home?failed=true&action=RegistrarVisita");
      return;
    }
    model.put("encargo_id",ctx.pathParam("encargo_id"));
    ctx.render("tecnico/registro_visita.hbs", model);
  }

  // POST /{encargo_id}/visita
  public void registrar(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    try {
      setModel(model, ctx);
      model.put("encargo_id", ctx.pathParam("encargo_id"));

      EncargoTecnico encargo = (EncargoTecnico) encargoTecnicoRepostiry
        .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")), EncargoTecnico.class);
      Logger.debug("Se comienza a registrar una visita al encargo de id: " + ctx.pathParam("encargo_id"));
      VisitaTecnica visitaTecnica = VisitaTecnica.builder()
        .descripcion(ctx.formParam("descripcion"))
        .exitosa(Boolean.valueOf(ctx.formParam("exitosa")))
        .fechaHora(LocalDateTime.parse(ctx.formParam("fechaHora")))
        .imagen(saveImage(ctx))
        .heladera(encargo.getIncidente().getHeladera())
        .tecnico(encargo.getTecnico())
        .build();

      if (visitaTecnica.getExitosa()) {
        Logger.debug("La visita fue exitosa => Se desactiva el encargo");

        // Si la visita fue exitosa cumplio el encargo entonces lo desactivo
        encargoTecnicoRepostiry.desactivar(encargo);
        // Y la heladera ya estaria reparada asi que la activamos
        encargo.getIncidente().getHeladera().activar();
      }

      encargo.incrementVisitasHechas();
      encargoTecnicoRepostiry.actualizar(encargo);
      encargoTecnicoRepostiry.refresh(encargo);
      repositorio.actualizar(encargo.getIncidente().getHeladera());

      model.put("popup_title", "Visita registrada");
      model.put("popup_message", "Visita registrada correctamente");
      model.put("popup_ruta", "/tecnico/home");
      model.put("popup_button", "Volver al inicio");
      visitaTecnicaRepository.guardar(visitaTecnica);
      ctx.render("tecnico/registro_visita.hbs", model);
    } catch (Exception e) {
      model.put("popup_title", "Error al registrar visita");
      model.put("popup_message", "No se pudo registrar la visita");
      model.put("popup_ruta", "/tecnico/home");
      model.put("popup_button", "Volver al inicio");

      ctx.render("tecnico/registro_visita.hbs", model);
    }
  }

  private void setModel(HashMap<String, Object> model, Context ctx) {
    model.put("esTecnico", true);
    model.put("username", ctx.sessionAttribute("user_name"));
    model.put("user_type", ctx.sessionAttribute("user_type"));
  }

  private void setVisitasEncargos(HashMap<String, Object> model, Context ctx) {
    List<VisitaTecnica> visitas = visitaTecnicaRepository.getPorTecnico(ctx.sessionAttribute("tecnico_id"));
    visitas.sort(Comparator.comparing(VisitaTecnica::getFechaHora));

    List<VisitaTecnicaDTO> visitasDTO = new ArrayList<>();
    for (VisitaTecnica visita : visitas) {
      if (visita.getActivo())
        visitasDTO.add(new VisitaTecnicaDTO(visita));
    }
    List<EncargoTecnico> encargos = encargoTecnicoRepostiry.getPorTecnico(ctx.sessionAttribute("tecnico_id"));
    encargos.sort(Comparator.comparing(EncargoTecnico::getFechaAlta));

    List<EncargoTecnicoDTO> encargosDTO = new ArrayList<>();
    for (EncargoTecnico encargo : encargos) {
      if (encargo.getActivo())
        encargosDTO.add(new EncargoTecnicoDTO(encargo));
    }

    model.put("visitas", visitasDTO);
    model.put("encargos", encargosDTO);
  }
  private void invalidarCacheNavegador(Context app) {
    app.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    app.header("Pragma", "no-cache");
    app.header("Expires", "0");
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
    String uploadDir = "src/main/resources/static/img/visitas/";
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

    String path = "/img/visitas/" + uploadedFile.filename();
    Logger.info("Se guardo el archivo correctamente");
    return path;
  }
}
