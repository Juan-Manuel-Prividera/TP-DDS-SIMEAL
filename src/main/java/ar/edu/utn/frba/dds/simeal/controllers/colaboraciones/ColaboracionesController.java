package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.ColaboracionDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
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
  private Repositorio repositorio;
  private final ColaboracionRepository colaboracionRepository;

  public ColaboracionesController(ColaboracionRepository colaboracionRepository, Repositorio repositorio) {
    this.colaboracionRepository = colaboracionRepository;
    this.repositorio = repositorio;
  }


  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    model.put("titulo", "SIMEAL - Colaboraciones");
    setNavBar(model, app);
    setFormasColaborar(model,app);
    String usrType = app.sessionAttribute("user_type");

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

  public void indexConfiguracion(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, ctx);

    ctx.render("/colaboraciones/configuracion.hbs", model);
  }

  public void configurarPreferencias(Context ctx) {
    Colaborador colaborador = (Colaborador) repositorio
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    List<TipoColaboracion> formasColaborar = new ArrayList<>();
    String donacionVianda = ctx.formParam("donarVianda");
    String donacionDinero= ctx.formParam("donarDinero");
    String distribucionVianda = ctx.formParam("distribucionVianda");
    String adherirHeladera = ctx.formParam("adherirHeladera");
    if (donacionVianda != null) {
      formasColaborar.add(TipoColaboracion.DONACION_VIANDA);
    }
    if (donacionDinero != null) {
      formasColaborar.add(TipoColaboracion.DINERO);
    }
    if (distribucionVianda != null) {
      formasColaborar.add(TipoColaboracion.REDISTRIBUCION_VIANDA);
    }
    if (adherirHeladera != null) {
      formasColaborar.add(TipoColaboracion.ADHERIR_HELADERA);
    }
    colaborador.setFormasDeColaborar(formasColaborar);
    repositorio.actualizar(colaborador);
    invalidarCacheNavegador(ctx);
    ctx.redirect("/colaboraciones");
  }

  private void setFormasColaborar(HashMap<String, Object> model, Context ctx) {
    Colaborador colaborador = (Colaborador) repositorio
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    if (colaborador.getFormasDeColaborar().isEmpty()) {
      model.put("distribuirVianda",true);
      model.put("adherirHeladera",true);
      model.put("donarDinero",true);
      model.put("donarVianda", true);
    } else {
      if (colaborador.getFormasDeColaborar().contains(TipoColaboracion.DONACION_VIANDA))
        model.put("donarVianda", true);

      if (colaborador.getFormasDeColaborar().contains(TipoColaboracion.DINERO))
        model.put("donarDinero", true);

      if (colaborador.getFormasDeColaborar().contains(TipoColaboracion.REDISTRIBUCION_VIANDA))
        model.put("distribuirVianda", true);

      if (colaborador.getFormasDeColaborar().contains(TipoColaboracion.ADHERIR_HELADERA))
        model.put("adherirHeladera", true);

    }
  }

  public void setNavBar(HashMap<String, Object> model, Context app) {
    model.put("colaboraciones", "seleccionado");
    model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
    if (app.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");
    else if (app.sessionAttribute("user_type") == "JURIDICO")
      model.put("esJuridico","true");

    model.put("username", app.sessionAttribute("user_name"));
  }

  private void invalidarCacheNavegador(Context app) {
    app.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    app.header("Pragma", "no-cache");
    app.header("Expires", "0");
  }
}