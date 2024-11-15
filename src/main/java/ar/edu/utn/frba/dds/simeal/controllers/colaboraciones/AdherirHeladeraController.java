package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.simeal.models.dtos.ModeloHeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdherirHeladeraController {
  private ColaboracionRepository colaboracionRepository;
  private Repositorio repositorio;

  public AdherirHeladeraController(ColaboracionRepository colaboracionRepository, Repositorio repositorio) {
    this.colaboracionRepository = colaboracionRepository;
    this.repositorio = repositorio;
  }

  // GET /colaboracion/adherirHeladera
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(ctx, model);
    setUbicaciones(ctx, model);
    setModelosHeladera(ctx, model);

    String failed = ctx.queryParam("failed");
    String action = ctx.queryParam("action");
    if (failed != null && action != null) {
      if (failed.equals("true") && action.equals("create")) {
        model.put("popup_title", "Error al adherir la heladera");
        model.put("popup_message", "Revise los campos ingresados e intente nuevamente");
        model.put("popup_ruta", "/colaboracion/adherirHeladera");
      } else if (failed.equals("false") && action.equals("create")) {
        model.put("popup_title", "Heladera adherida correctamente");
        model.put("popup_ruta", "/colaboraciones");
      }
    }
    ctx.render("colaboraciones/adherirHeladera.hbs", model);
  }
  // POST /colaboracion/adherirHeladera
  public void create(Context ctx) {
    try{
      String nombre = ctx.formParam("nombreHeladera");
      Long modeloId = Long.valueOf(ctx.formParam("modeloHeladera"));
      Long ubicacionId = Long.valueOf(ctx.formParam("ubicacionHeladera"));

      ModeloHeladera modelo = (ModeloHeladera) repositorio.buscarPorId(modeloId, ModeloHeladera.class);
      Ubicacion ubicacion = (Ubicacion) repositorio.buscarPorId(ubicacionId, Ubicacion.class);

      ServiceLocator.getController(HeladeraController.class).create(ctx, nombre, modelo, ubicacion);

      ctx.redirect("/colaboracion/adherirHeladera?failed=false&action=create");
    }catch (Exception e){
      ctx.redirect("/colaboracion/adherirHeladera?failed=true&action=create");
    }
  }

  private void setModel(Context ctx, HashMap<String, Object> model) {
    model.put("colaboraciones", "seleccionado");
    model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());
    if (ctx.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano", "true");
    else if (ctx.sessionAttribute("user_type") == "JURIDICO")
      model.put("esJuridico", "true");

    model.put("username", ctx.sessionAttribute("user_name"));
  }

  private void setModelosHeladera(Context ctx, HashMap<String, Object> model) {
    Long colabId = ctx.sessionAttribute("colaborador_id");
    List<ModeloHeladera> modelosDispoibles = (List<ModeloHeladera>) repositorio.obtenerTodos(ModeloHeladera.class);
    List<ModeloHeladeraDTO> modelos = new ArrayList<>();
    for (ModeloHeladera mh : modelosDispoibles) {
      if (mh.getActivo()) {
        modelos.add(new ModeloHeladeraDTO(mh));
      }
    }
    model.put("modelosHeladera", modelos);
  }

  private void setUbicaciones(Context ctx, HashMap<String, Object> model) {
    Long colabId = ctx.sessionAttribute("colaborador_id");
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(colabId, Colaborador.class);
    List<UbicacionDTO> ubicaciones = new ArrayList<>();
    for (Ubicacion ubicacion : colaborador.getUbicaciones()) {
      ubicaciones.add(new UbicacionDTO(ubicacion));
    }
    model.put("ubicacionesPosibles", ubicaciones);
  }

}
