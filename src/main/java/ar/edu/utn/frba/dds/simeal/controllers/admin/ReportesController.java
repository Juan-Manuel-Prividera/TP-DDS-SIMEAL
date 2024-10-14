package ar.edu.utn.frba.dds.simeal.controllers.admin;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ReportesController {
  public void reportes(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("reporte", "seleccionado");
    model.put("user_type", "admin");
    model.put("esAdmin", true);
    model.put("username", "Administrador");
    model.put("titulo", "Reportes");
    app.render("admin/reportes.hbs", model);
  }
}
