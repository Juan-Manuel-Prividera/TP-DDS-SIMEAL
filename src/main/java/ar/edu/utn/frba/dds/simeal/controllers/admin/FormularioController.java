package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.FormularioDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.FormularioRepository;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormularioController {
  private final FormularioRepository repositorio;

  public FormularioController(FormularioRepository repositorio) {
    this.repositorio = repositorio;
  }
  // /formuarios
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setFormulario(model,ctx);
    model.put("titulo", "Formularios");
    model.put("formulario", "seleccionado");
    ctx.render("admin/formularios.hbs",model);
  }
  // POST /formulario
  public void crearFormulario(Context ctx) {
    Formulario formulario;
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
    ctx.redirect("/formularios");
  }

  // formulario/{formulario_id}
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
    ctx.render("admin/creacion_preguntas.hbs", model);
  }

  // post formulario/{formulario_id}/pregunta
  public void crearPregunta(Context ctx) {
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    if (formulario == null) {
      ctx.status(404).result("Formulario no encontrado");
      return;
    }

    List<Opcion> opciones = new ArrayList<>();
    List<String> opcionesForm = ctx.formParams("opciones[]");

    if (!opcionesForm.isEmpty()) {
      for (String opcion : opcionesForm) {
        opciones.add(new Opcion(opcion));
      }
    } else {
      ctx.status(400).result("No se proporcionaron opciones válidas");
      return;
    }

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
    ctx.redirect("/formulario/" + formulario.getId());
  }

  public void borrarPregunta(Context ctx) {
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    Pregunta pregunta = (Pregunta) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("pregunta_id")), Pregunta.class);
    try {
      formulario.getPreguntas().remove(pregunta);
      repositorio.actualizar(formulario);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    ctx.status(200);
  }

  public void borrarFormulario(Context ctx) {
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    try {
      repositorio.desactivar(formulario);
      ctx.status(200);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("user_type", "admin");
    model.put("esAdmin", true);
    model.put("username", ctx.sessionAttribute("user_name"));
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
