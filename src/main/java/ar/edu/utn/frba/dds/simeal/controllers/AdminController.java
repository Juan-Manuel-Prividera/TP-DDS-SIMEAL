package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.cargadordatos.LectorCsv;
import com.opencsv.exceptions.CsvException;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminController  {

  public void index(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "seleccionado");
    model.put("reporte", "");
    model.put("cambiomodo", "");
    app.render("admin/cargar_colaboraciones.hbs", model);
  }
  public void reportes(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "");
    model.put("reporte", "seleccionado");
    model.put("cambiomodo", "");
    app.render("admin/reportes.hbs", model);

  }
  public void cambiarmodo(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "");
    model.put("reporte", "");
    model.put("cambiomodo", "seleccionado");
    app.render("admin/cambiardemodo.hbs", model);

  }

  public void migrarColaboraciones(Context app) throws IOException, CsvException {
    app.redirect("/simeal/migracion");
    UploadedFile uploadedFile = app.uploadedFile("file");
    if (uploadedFile != null) {
      String filepath = "src/main/resources/migration" + uploadedFile.filename();
      File file = new File(filepath);
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(uploadedFile.content().readAllBytes());

      ServiceLocator.getService(LectorCsv.class).leerColaboradores(filepath);
    }
  }
}
