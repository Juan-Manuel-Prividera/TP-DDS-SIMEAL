package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.cargadordatos.LectorCsv;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import com.opencsv.exceptions.CsvException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MigracionController {
  // GET /migracion
  public void index(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "seleccionado");
    model.put("user_type", app.sessionAttribute("user_type"));
    model.put("esAdmin", true);
    model.put("username", app.sessionAttribute("user_name"));
    model.put("titulo", "Migracion Colaboraciones");
    String failed = app.queryParam("failed");
    String action = app.queryParam("action");
    Logger.debug("Failed = " + failed);
    if (Objects.equals(failed, "true") && Objects.equals(action, "validate")) {
      model.put("popup_title", "Error en carga de CSV");
      model.put("popup_message", "Ocurrio un error al validar el formato del csv");
      model.put("popup_ruta", "/migracion");
    } else if (Objects.equals(failed, "false") && Objects.equals(action, "validate")) {
      model.put("popup_title", "Migración completada exitosamente :)");
      model.put("popup_ruta", "/migracion");
    } else if (Objects.equals(failed, "true") && Objects.equals(action, "conexion")) {
      model.put("popup_title", "Error de conexión");
      model.put("popup_message", "Ocurrio un error de conexión con el servidor :( Fijate loco sos el admin...");
      model.put("popup_ruta", "/migracion");
    }

    app.render("admin/cargar_colaboraciones.hbs", model);
  }
  // POST migracion/upload
  public void migrarColaboraciones(Context app) throws IOException, CsvException {
    UploadedFile uploadedFile = app.uploadedFile("file");
    if (uploadedFile != null) {
      String filepath = "src/main/resources/migration/" + uploadedFile.filename();
      if (!uploadedFile.filename().endsWith(".csv")) {
        app.status(HttpStatus.FOUND);
        Logger.debug("No es un csv...");
        return;
      }

      File file = new File(filepath);
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(uploadedFile.content().readAllBytes());
      if (!file.getName().endsWith(".csv")) {
        app.status(HttpStatus.FOUND);
        Logger.debug("No es un csv...");
        return;
      }
      LectorCsv lectorCsv = ServiceLocator.getService(LectorCsv.class);

      if (lectorCsv.validarFormato(file)) {
        app.status(200);
        Logger.debug("Responde 200OK a validar formato de csv");
      } else {
        Logger.debug("Responde 400 a validar formato de csv");
        app.status(HttpStatus.FOUND);
        return;
      }

      Logger.debug("Comenzando el proceso de migracion de colaboraciones");
      List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();
      ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
      colaboraciones = lectorCsv.leerColaboradores(filepath);
      Logger.debug("Hay: " + colaboraciones.size() + " colaboraciones en el csv");

      colaboracionRepository.guardar(colaboraciones);

      notificarAdmin();
      Logger.info("Migracion de colaboraciones completada exitosamente");
    } else {
      Logger.error("No se encontro el archivo para realizar la migracion");
      app.status(HttpStatus.FOUND);
    }
  }

  private void notificarAdmin() {
    ConfigReader configReader = new ConfigReader();
    String email = configReader.getProperty("admin.email");
    Mensaje mensaje = new Mensaje("La migración de datos se ha completado", "Migración de datos");
    // TODO: DESCOMENTAR PROXIMAMENTE
//    ServiceLocator.getService(EnviadorDeMails.class).enviar(email,mensaje);
  }
}
