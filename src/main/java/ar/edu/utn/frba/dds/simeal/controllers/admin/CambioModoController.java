package ar.edu.utn.frba.dds.simeal.controllers.admin;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class CambioModoController {

  public void cambiarmodo(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("cargacsv", "");
    model.put("reporte", "");
    model.put("cambiomodo", "seleccionado");
    model.put("esAdmin", true);
    model.put("user_type", "admin");
    model.put("username", app.sessionAttribute("user_name"));
    model.put("titulo", "Cambio de Modo");
    app.render("admin/cambiardemodo.hbs", model);

  }
}
