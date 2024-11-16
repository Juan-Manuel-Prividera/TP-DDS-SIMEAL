package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.ColaboracionDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ColaboracionesController {

  private final ColaboracionRepository colaboracionRepository;

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
    if (usrType.equals("HUMANO")) {
      model.put("esHumano", true);
    }

    app.render("/colaboraciones/colaboraciones.hbs", model);
  }
  // TODO: Sacar de aca
  public void mostrarFormularioDonacionVianda(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Donar Vianda");
    setNavBar(model, app);

    app.render("/colaboraciones/donarVianda.hbs", model);
  }
  // TODO: Sacar de aca
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

  // TODO: Sacar de aca
  public void mostrarFormularioDistribucionVianda(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Distribuir Vianda");
    setNavBar(model, app);

    app.render("/colaboraciones/distribuirVianda.hbs", model);
  }
  // TODO: Sacar de aca
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

  public void indexHistorial(Context ctx) {
    List<ColaboracionPuntuable> colaboraciones = (List<ColaboracionPuntuable>) colaboracionRepository
      .getAllPorColaborador(ctx.sessionAttribute("colaborador_id"));
    List<ColaboracionDTO> colaboracionDTOS = new ArrayList<>();
    for(ColaboracionPuntuable colaboracionPuntuable : colaboraciones) {
        colaboracionDTOS.add(new ColaboracionDTO(colaboracionPuntuable));
    }
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, ctx);
    model.put("historial", colaboracionDTOS);

    ctx.render("/colaboraciones/historial.hbs", model);
  }

  public void setNavBar(HashMap<String, Object> model, Context app) {
    model.put("colaboraciones", "seleccionado");
    model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
    if (app.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");
    else if (app.sessionAttribute("user_type") == "JURIDICO")
      model.put("esJuridico","true");

    model.put("username", app.sessionAttribute("user_name"));  }
}