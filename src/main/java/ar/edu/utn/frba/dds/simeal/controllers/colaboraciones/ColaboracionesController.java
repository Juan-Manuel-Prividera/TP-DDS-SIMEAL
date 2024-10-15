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
      // TODO: Qué pasa si el admin quiere ver esta página ? -> no puede
      app.status(404).result("Tipo de usuario no válido");
    }
  }

  public void mostrarFormularioDonacionDinero(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Dinero");

    app.render("/colaboraciones/donarDinero.hbs", model);
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

    app.render("/colaboraciones/confirmacion_donacion.hbs", model);
  }

  public void mostrarFormularioDonacionVianda(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Vianda");

    app.render("/colaboraciones/donarVianda.hbs", model);
  }

  public void donarVianda(Context app) {
    // Obtener los datos del formulario
    String nombreHeladera = app.formParam("nombre_heladera");
    String direccionHeladera = app.formParam("direccion");

    // Lógica para registrar la donación

    Map<String, Object> model = new HashMap<>();
    model.put("nombreHeladera", nombreHeladera);
    model.put("direccionHeladera", direccionHeladera);
    model.put("mensaje", "Gracias por tu donación a la heladera " + nombreHeladera + " ubicada en " + direccionHeladera);

    app.render("/colaboraciones/confirmacion_donacion.hbs", model);
  }

  public void mostrarFormularioDistribucionVianda(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Distribuir Vianda");

    app.render("/colaboraciones/distribuirVianda.hbs", model);
  }

  public void distribuirVianda(Context ctx) {
    // Procesar datos del formulario
    String heladeraOrigen = ctx.formParam("heladeraOrigen");
    String direccionOrigen = ctx.formParam("direccionOrigen");
    String heladeraDestino = ctx.formParam("heladeraDestino");
    String direccionDestino = ctx.formParam("direccionDestino");

    // Aquí guardas los datos en tu clase Repositorio

    Map<String, Object> model = new HashMap<>();
    model.put("heladeraOrigen", heladeraOrigen);
    model.put("heladeraDestino", heladeraDestino);
    model.put("direccionOrigen", direccionOrigen);
    model.put("direccionDestino", direccionDestino);
    model.put("mensaje", "Gracias por tu distribucion de la heladera " + heladeraOrigen + " ubicada en " + direccionOrigen + "a la heladera " + heladeraDestino + " ubicada en " + direccionDestino);

    ctx.render("templates/confirmacion_Distribucion.hbs");
  }

  public void mostrarFormularioAdherirHeladera(Context app) {
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Adherir Heladera");

    app.render("/colaboraciones/adherirHeladera.hbs", model);
  }

  public void adherirHeladera(Context app) {
    // Obtener los datos del formulario
    String nombreHeladera = app.formParam("nombreHeladera");
    String ubicacionHeladera = app.formParam("ubicacionHeladera");
    String modeloHeladera = app.formParam("modeloHeladera");

    // Lógica para registrar la nueva heladera
    // Aquí puedes agregar la heladera a la base de datos o repositorio

    Map<String, Object> model = new HashMap<>();
    model.put("nombreHeladera", nombreHeladera);
    model.put("ubicacionHeladera", ubicacionHeladera);
    model.put("modeloHeladera", modeloHeladera);
    model.put("mensaje", "La heladera " + nombreHeladera + " ha sido adherida exitosamente.");

    app.render("/colaboraciones/confirmacion_adherencia.hbs", model);
  }

}