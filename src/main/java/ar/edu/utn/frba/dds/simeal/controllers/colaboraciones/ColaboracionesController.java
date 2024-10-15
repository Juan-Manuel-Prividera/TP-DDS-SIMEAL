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
    String usrType = app.sessionAttribute("user_type");

    // Renderizar la vista correspondiente según el tipo de usuario
    if (usrType.equals("juridico")) {
      app.render("/colaboraciones/colaboraciones_juridico.hbs", model);
    } else if (usrType.equals("personal")) {
      app.render("/colaboraciones/colaboraciones_personal.hbs", model);
    } else {
      app.status(404).result("Tipo de usuario no válido");
    }
  }

  public void donarDinero(Context app) {
    String metodoPago = app.formParam("metodo_pago");
    String monto = app.formParam("monto");

    // lógica para procesar el pago según el método seleccionado.

    // Crear un modelo para pasar a la vista
    Map<String, Object> model = new HashMap<>();
    model.put("monto", monto);
    model.put("metodoPago", metodoPago);
    model.put("mensaje", "Gracias por tu donación de " + monto + " ARS usando " + metodoPago);

    // Renderizar la página de confirmación o agradecimiento
    app.render("/colaboraciones/donarDinero.hbs", model);
  }
}