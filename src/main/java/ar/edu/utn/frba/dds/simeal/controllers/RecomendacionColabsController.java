package ar.edu.utn.frba.dds.simeal.controllers;

import io.javalin.http.Context;

import java.util.HashMap;

public class RecomendacionColabsController {
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Recomendacion de Colaboradores");
    model.put("username", ctx.sessionAttribute("user_name"));
    model.put("esJuridico", true);
    model.put("recomendacion", "seleccionado");
    ctx.render("recomendacion_colaboradores.hbs", model);
  }
}
