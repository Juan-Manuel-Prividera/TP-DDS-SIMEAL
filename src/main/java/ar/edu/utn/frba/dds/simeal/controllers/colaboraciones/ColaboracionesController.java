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

  public void mostrarFormularioDonacionVianda(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Vianda");

    // Renderizar la vista donde el usuario puede seleccionar una heladera y donar una vianda
    app.render("/colaboraciones/donarVianda.hbs", model);
  }

  public void donarVianda(Context app) {
    // Obtener los datos del formulario
    String nombreHeladera = app.formParam("nombre_heladera");
    String direccionHeladera = app.formParam("direccion");

    // Lógica para registrar la donación
    // Aquí podrías, por ejemplo, guardar la donación en una base de datos

    // Crear el modelo para la vista de confirmación
    Map<String, Object> model = new HashMap<>();
    model.put("nombreHeladera", nombreHeladera);
    model.put("direccionHeladera", direccionHeladera);
    model.put("mensaje", "Gracias por tu donación a la heladera " + nombreHeladera + " ubicada en " + direccionHeladera);

    // Renderizar la página de confirmación
    app.render("/colaboraciones/donarVianda.hbs", model);
  }


}