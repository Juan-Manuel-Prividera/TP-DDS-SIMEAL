package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
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
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigracionController {
  public void index(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "seleccionado");
    model.put("user_type", app.sessionAttribute("user_type"));
    model.put("esAdmin", true);
    model.put("username", app.sessionAttribute("user_name"));
    model.put("titulo", "Migracion Colaboraciones");
    app.render("admin/cargar_colaboraciones.hbs", model);
  }

  public void migrarColaboraciones(Context app) throws IOException, CsvException {
    UploadedFile uploadedFile = app.uploadedFile("file");
    if (uploadedFile != null) {
      Logger.debug("Comenzando el proceso de migracion de colaboraciones");
      String filepath = "src/main/resources/migration/" + uploadedFile.filename();
      File file = new File(filepath);
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(uploadedFile.content().readAllBytes());

      LectorCsv lectorCsv = ServiceLocator.getService(LectorCsv.class);

      if (lectorCsv.validarFormato(file)) {
        app.status(200);
        // TODO: Chequear bien que esto no termine el metodo
        app.redirect("/migracion");
      } else {
        app.status(404);
        return;
      }

      List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();
      ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
      colaboraciones = lectorCsv.leerColaboradores(filepath);

      for (ColaboracionPuntuable colaboracion : colaboraciones) {
        colaboracionRepository.guardar((Persistente) colaboracion);
      }
      // Aviso al Admin que ya esta todo cargado
      notificarAdmin();
      Logger.info("Migracion de colaboraciones completada exitosamente");
    } else {
      Logger.error("No se encontro el archivo para realizar la migracion");
      app.status(400);
    }
  }

  private void notificarAdmin() {
    ConfigReader configReader = new ConfigReader();
    String email = configReader.getProperty("admin.email");
    Mensaje mensaje = new Mensaje("La migración de datos se ha completado", "Migración de datos");
    ServiceLocator.getService(EnviadorDeMails.class).enviar(email, mensaje);
  }
}
