package ar.edu.utn.frba.dds.simeal.controllers.admin;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ReportesController {
  public void reportes(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "");
    model.put("reporte", "seleccionado");
    model.put("cambiomodo", "");

    app.render("admin/reportes.hbs", model);

  }
}
