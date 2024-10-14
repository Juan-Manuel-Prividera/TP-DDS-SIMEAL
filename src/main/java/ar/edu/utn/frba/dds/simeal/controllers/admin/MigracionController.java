package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.utils.cargadordatos.LectorCsv;
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
    model.put("user_type", "admin");
    model.put("esAdmin", true);
    model.put("username", "Administrador");
    model.put("titulo", "Migracion Colaboraciones");
    app.render("admin/cargar_colaboraciones.hbs", model);
  }
  public void migrarColaboraciones(Context app) throws IOException, CsvException {
    app.redirect("/simeal/migracion");
    UploadedFile uploadedFile = app.uploadedFile("file");
    if (uploadedFile != null) {
      String filepath = "src/main/resources/migration/" + uploadedFile.filename();
      File file = new File(filepath);
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(uploadedFile.content().readAllBytes());

      List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();
      ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
      colaboraciones = ServiceLocator.getService(LectorCsv.class).leerColaboradores(filepath);

      for (ColaboracionPuntuable colaboracion : colaboraciones) {
        colaboracionRepository.guardar((Persistente) colaboracion);

      }
    }
  }
}
