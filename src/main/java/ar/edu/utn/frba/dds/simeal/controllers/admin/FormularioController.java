package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.FormularioDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.FormularioRepository;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FormularioController {
  private final FormularioRepository repositorio;

  public FormularioController(FormularioRepository repositorio) {
    this.repositorio = repositorio;
  }
  // /formuarios
  public void index(Context ctx) {
    Logger.debug("Entra a indexxxx");
    HashMap<String, Object> model = new HashMap<>();
    setFormulario(model,ctx);
    setNavBar(model,ctx);
    model.put("titulo", "Formularios");
    model.put("formulario", "seleccionado");

    String failed = ctx.queryParam("failed");
    String action = ctx.queryParam("action");
    Logger.debug("Failed: " + failed);
    Logger.debug("Action: " + action);
    if (Objects.equals(failed, "false") && Objects.equals(action, "createForm")) {
      model.put("popup_title", "Formulario creado exitosamente");

    } else if (Objects.equals(failed, "true") && Objects.equals(action, "createForm")) {
      model.put("popup_title", "Error en creacion de formulario");
      model.put("popup_message", "Ocurrio un error al crear el formulario :(");

    } else if (Objects.equals(failed, "true") && Objects.equals(action, "searchForm")) {
      model.put("popup_title", "Error al buscar formulario");
      model.put("popup_message", "No se encontro el formulario solicitado :(");

    } else if (Objects.equals(failed, "true") && Objects.equals(action, "deleteForm")) {
      model.put("popup_title", "Error al eliminar formulario");
      model.put("popup_message", "Ocurrio un error al eliminar formulario :(");

    } else if (Objects.equals(failed, "false") && Objects.equals(action, "deleteForm")) {
      Logger.debug("Entra a poner popup");
      model.put("popup_title", "Formulario eliminado exitosamente");
    }
    model.put("popup_ruta", "/formularios");
    ctx.render("admin/formularios.hbs",model);
  }
  // POST /formulario
  public void crearFormulario(Context ctx) {
    Formulario formulario;
    try {
      if (ctx.formParam("rol").equals("HUMANO")) {
        formulario = Formulario.builder()
          .enUso(true)
          .rol(TipoRol.HUMANO)
          .nombre(ctx.formParam("nombreFormulario"))
          .build();
        repositorio.guardar(formulario);
        desactivarFormularios(formulario);
      } else if (ctx.formParam("rol").equals("JURIDICO")) {
        formulario = Formulario.builder()
          .enUso(true)
          .rol(TipoRol.JURIDICO)
          .nombre(ctx.formParam("nombreFormulario"))
          .build();
        repositorio.guardar(formulario);
        desactivarFormularios(formulario);
      }
      ctx.redirect("/formularios?failed=false&action=createForm");
    } catch (Exception e) {
      Logger.error("Error creando un formulario: " + e.getMessage());
      ctx.redirect("/formularios?failed=true&action=createForm");
    }
  }

  // GET formulario/{formulario_id}
  public void editarFormulario(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,ctx);
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    repositorio.refresh(formulario);
    List<PreguntaDTO> preguntas = new ArrayList<>();
    for(Pregunta pregunta : formulario.getPreguntas()) {
      preguntas.add(new PreguntaDTO(pregunta));
    }
    model.put("preguntas", preguntas);
    model.put("formulario_id", ctx.pathParam("formulario_id"));
    model.put("titulo", "Preguntas Formulario");

    String failed = ctx.queryParam("failed");
    String action = ctx.queryParam("action");
    Logger.debug("Failed: " + failed + " Action: " + action);
    if (action != null) model.put("popup_ruta", "/formulario/" + ctx.pathParam("formulario_id"));

    if (Objects.equals(failed, "false") && Objects.equals(action, "create")) {
      model.put("popup_title", "Pregunta creada exitosamente");
    } else if (Objects.equals(failed, "true") && Objects.equals(action, "create")) {
      model.put("popup_title", "Error en creacion de pregunta");
      model.put("popup_message", "Ocurrio un error al crear la pregunta :(");
    } else if (Objects.equals(failed, "true") && Objects.equals(action, "delete")) {
      model.put("popup_title", "Error al borrar pregunta");
      model.put("popup_message", "Ocurrio un rrror al borrar la pregunta :(");
    } else if (Objects.equals(failed, "false") && Objects.equals(action, "delete")) {
      model.put("popup_title", "Pregunta borrada exitosamente");
      Logger.debug("PONE EL POPUP DE PREGUNTA BORRADA...");
      Logger.debug("Estado del model: " + model);
    }
    ctx.render("admin/creacion_preguntas.hbs", model);
  }

  // post formulario/{formulario_id}/pregunta
  public void crearPregunta(Context ctx) {
    Formulario formulario;
    try {
      formulario = (Formulario) repositorio
        .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
      if (formulario == null) throw new RuntimeException("No se encontro formulario");
    } catch (Exception e) {
      ctx.redirect("/formularios?failed=true&action=searchForm");
      return;
    }
    try {
      List<Opcion> opciones = new ArrayList<>();
      List<String> opcionesForm = ctx.formParams("opciones[]");

      if (!opcionesForm.isEmpty()) {
        for (String opcion : opcionesForm) {
          opciones.add(new Opcion(opcion));
        }
      } else throw new RuntimeException("No se proporcionaron opciones validas");

      Pregunta pregunta = Pregunta.builder()
        .tipo(obtenerTipoPregunta(ctx.formParam("tipoPregunta")))
        .param(ctx.formParam("parametro"))
        .pregunta(ctx.formParam("pregunta"))
        .required(false)
        .opciones(opciones)
        .build();

      repositorio.guardar(pregunta);
      formulario.addPregunta(pregunta);
      repositorio.actualizar(formulario);
      repositorio.refresh(formulario);
      ctx.redirect("/formulario/" + formulario.getId() + "?failed=false&action=create");
    } catch (Exception e) {
      ctx.redirect("/formulario/" + formulario.getId() + "?failed=true&action=create");
    }
  }

  public void borrarPregunta(Context ctx) {
    Formulario formulario;
    try {
      Logger.debug("Entra a borrar pregunta");
      formulario = (Formulario) repositorio.buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
      Logger.debug("Formulario obtenido: " + formulario);
    } catch (Exception e) {
      ctx.redirect("/formularios?failed=true&action=searchForm");
      return;
    }
    try {
      Pregunta pregunta = (Pregunta) repositorio
        .buscarPorId(Long.valueOf(ctx.pathParam("pregunta_id")), Pregunta.class);

      Logger.debug("Pregunta obtenido: " + pregunta);
      formulario.getPreguntas().remove(pregunta);
      repositorio.actualizar(formulario);
      repositorio.refresh(formulario);
      Logger.debug("Actualizo todo...");
      ctx.redirect("/formulario/" + formulario.getId() + "?failed=false&action=delete");
    } catch (Exception e) {
      ctx.redirect("/formulario/" + formulario.getId() + "?failed=true&action=delete");
    }
  }

  public void borrarFormulario(Context ctx) {
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    try {
      Logger.debug("Entra a borrar formulario de id: " + formulario.getId());
      repositorio.desactivar(formulario);
      repositorio.refresh(formulario);
      Logger.debug("Antes de hacer el redirect luego de eliminar form");
      ctx.redirect("/formularios?failed=false&action=deleteForm");
    } catch (Exception e) {
      Logger.error("Rompe al eliminar formulario: " + e.getMessage());
      ctx.redirect("/formularios?failed=true&action=deleteForm");
    }
  }

  public void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("user_type", "admin");
    model.put("esAdmin", true);
    model.put("username", ctx.sessionAttribute("user_name"));
    model.put("formulario", "seleccionado");
  }


  private void setFormulario(HashMap<String, Object> model, Context ctx) {
    List<Formulario> formularios = (List<Formulario>) repositorio.obtenerTodos(Formulario.class);
    List<FormularioDTO> formulariosDtos = new ArrayList<>();
    for (Formulario formulario : formularios) {
      formulariosDtos.add(new FormularioDTO(formulario));
    }

    model.put("formularios", formulariosDtos);
    setNavBar(model, ctx);
  }

  private TipoPregunta obtenerTipoPregunta(String tipoPregunta) {
    return switch (tipoPregunta) {
      case "abierta" -> TipoPregunta.TEXT;
      case "multiple" -> TipoPregunta.CHOICE;
      default -> null;
    };
  }

  private void desactivarFormularios(Formulario formulario) {
    List<Formulario> formularios = (List<Formulario>) repositorio.obtenerTodos(Formulario.class);
    for (Formulario f : formularios) {
      if (f.getRol().equals(formulario.getRol()) && !f.getNombre().equals(formulario.getNombre())) {
        f.setEnUso(false);
      }
    }
  }
}
