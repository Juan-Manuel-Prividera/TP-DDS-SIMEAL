package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;


public class ColaboracionesController {

  private ColaboracionRepository colaboracionRepository;

  public ColaboracionesController(ColaboracionRepository colaboracionRepository) {
    this.colaboracionRepository = colaboracionRepository;
  }


  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    model.put("titulo", "SIMEAL - Colaboraciones");
    setNavBar(model, app);

    // Verificar el tipo de usuario desde el path param
    String usrType = app.sessionAttribute("user_type");
    System.out.println(usrType);
    // Renderizar la vista correspondiente según el tipo de usuario
    if (usrType.equals("JURIDICO")) {
      app.render("/colaboraciones/colaboracion_juridico.hbs", model);
    } else if (usrType.equals("HUMANO")) {
      app.render("/colaboraciones/colaboraciones_personal.hbs", model);
    } else {
      // TODO: Qué pasa si el admin quiere ver esta página ? -> no puede
      app.status(404).result("Tipo de usuario no válido");
    }
  }

  public void mostrarFormularioDonacionDinero(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Dinero");
    setNavBar(model, app);

    app.render("/colaboraciones/donarDinero.hbs", model);
  }

  public void donarDinero(Context app) {
    String metodoPago = app.formParam("metodoPago");
    String monto = app.formParam("monto");

    if (metodoPago == null || monto == null) {
      app.render("impostor_among_us.hbs");
      return;
    }


    Integer montoParsed = Integer.parseInt(monto);

    if (montoParsed <= 0) {
      HashMap<String, Object> model = new HashMap<>();
      model.put("popup_title", "La concha de tu madre");
      model.put("popup_message", "Gracias por tu donación de " + monto + " AR$ \uD83D\uDC80");
      setNavBar(model, app);

      app.render("/colaboraciones/donarDinero.hbs", model);
      return;

    }

    Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
    Colaborador colaborador = (Colaborador) repo.buscarPorId(app.sessionAttribute("colaborador_id"), Colaborador.class);

    DonarDinero donacion = DonarDinero.create(colaborador, LocalDate.now(), montoParsed);
    repo.guardar(donacion);

    // Crear un modelo para pasar a la vista
    HashMap<String, Object> model = new HashMap<>();
    model.put("popup_title", "Donación realizada ❤");
    model.put("popup_message", "Gracias por tu donación de " + monto + " ARS usando " + metodoPago);
    setNavBar(model, app);

    app.render("/colaboraciones/donarDinero.hbs", model);

  }

  public void mostrarFormularioDonacionVianda(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Vianda");
    setNavBar(model, app);

    app.render("/colaboraciones/donarVianda.hbs", model);
  }

  public void donarVianda(Context app) {
    // Obtener los datos del formulario
    String nombreHeladera = app.formParam("nombre_heladera");
    String direccionHeladera = app.formParam("direccion");

    // Lógica para registrar la donación

    HashMap<String, Object> model = new HashMap<>();
    model.put("nombreHeladera", nombreHeladera);
    model.put("direccionHeladera", direccionHeladera);
    model.put("mensaje", "Gracias por tu donación a la heladera " + nombreHeladera + " ubicada en " + direccionHeladera);
    setNavBar(model, app);

    app.render("/colaboraciones/confirmacion_donacion.hbs", model);
  }

  public void mostrarFormularioDistribucionVianda(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Distribuir Vianda");
    setNavBar(model, app);

    app.render("/colaboraciones/distribuirVianda.hbs", model);
  }

  public void distribuirVianda(Context ctx) {
    // Procesar datos del formulario
    String heladeraOrigen = ctx.formParam("heladeraOrigen");
    String direccionOrigen = ctx.formParam("direccionOrigen");
    String heladeraDestino = ctx.formParam("heladeraDestino");
    String direccionDestino = ctx.formParam("direccionDestino");

    // Aquí guardas los datos en tu clase Repositorio

    HashMap<String, Object> model = new HashMap<>();
    model.put("heladeraOrigen", heladeraOrigen);
    model.put("heladeraDestino", heladeraDestino);
    model.put("direccionOrigen", direccionOrigen);
    model.put("direccionDestino", direccionDestino);
    model.put("mensaje", "Gracias por tu distribucion de la heladera " + heladeraOrigen + " ubicada en " + direccionOrigen + "a la heladera " + heladeraDestino + " ubicada en " + direccionDestino);
    setNavBar(model, ctx);

    ctx.render("templates/confirmacion_Distribucion.hbs");
  }

  public void mostrarFormularioAdherirHeladera(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Adherir Heladera");
    setNavBar(model, app);

    app.render("/colaboraciones/adherirHeladera.hbs", model);
  }

  public void adherirHeladera(Context app) {
    // Obtener los datos del formulario
    String nombreHeladera = app.formParam("nombreHeladera");
    String ubicacionHeladera = app.formParam("ubicacionHeladera");
    String modeloHeladera = app.formParam("modeloHeladera");

    // Lógica para registrar la nueva heladera
    // Aquí puedes agregar la heladera a la base de datos o repositorio

    HashMap<String, Object> model = new HashMap<>();
    model.put("nombreHeladera", nombreHeladera);
    model.put("ubicacionHeladera", ubicacionHeladera);
    model.put("modeloHeladera", modeloHeladera);
    model.put("mensaje", "La heladera " + nombreHeladera + " ha sido adherida exitosamente.");
    setNavBar(model, app);
    app.render("/colaboraciones/confirmacion_adherencia.hbs", model);
  }

  public void setNavBar(HashMap<String, Object> model, Context app) {
    model.put("colaboraciones", "seleccionado");
    model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
    if (app.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");
    else if (app.sessionAttribute("user_type") == "JURIDICO")
      model.put("esJuridico","true");

    model.put("username", app.sessionAttribute("username"));  }
}