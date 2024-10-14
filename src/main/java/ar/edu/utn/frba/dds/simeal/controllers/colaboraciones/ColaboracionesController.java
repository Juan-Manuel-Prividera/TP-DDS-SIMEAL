package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;


public class ColaboracionesController {

  public void index(Context app) {
    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "SIMEAL - Colaboraciones");

    // Verificar el tipo de usuario desde el path param
    String usrType = app.pathParam("usr_type");

    // Renderizar la vista correspondiente según el tipo de usuario
    if (usrType.equals("juridico")) {
      app.render("/colaboraciones/colaboraciones_juridico.hbs", model);
    } else if (usrType.equals("personal")) {
      app.render("/colaboraciones/colaboraciones_personal.hbs", model);
    } else {
      app.status(404).result("Tipo de usuario no válido");
    }
  }
}