package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.FormularioDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import com.twilio.twiml.voice.Prompt;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormularioController {
  private final Repositorio repositorio;

  public FormularioController(Repositorio repositorio) {
    this.repositorio = repositorio;
  }

  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setFormulario(model);

    ctx.render("admin/formularios.hbs",model);
  }

  public void crearFormulario(Context ctx) {
    Formulario formulario = Formulario.builder()
      .enUso(true)
      .nombre(ctx.formParam("nombreFormulario"))
      .build();
    repositorio.guardar(formulario);
  }

  public void editarFormulario(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setFormulario(model);
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);
    List<PreguntaDTO> preguntas = new ArrayList<>();
    for(Pregunta pregunta : formulario.getPreguntas()) {
      preguntas.add(new PreguntaDTO(pregunta));
    }
    model.put("preguntas", preguntas);
    model.put("formulario_id", ctx.pathParam("formulario_id"));
    ctx.render("admin/creacion_preguntas.hbs", model);
  }


  public void crearPregunta(Context ctx) {
    List<Opcion> opciones = new ArrayList<>();
    List<String> opcionesForm = ctx.formParams("opciones[]");
    Formulario formulario = (Formulario) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("formulario_id")), Formulario.class);


    for (String opcion : opcionesForm) {
      opciones.add(new Opcion(opcion));
    }

    Pregunta pregunta = Pregunta.builder()
     .tipo(obtenerTipoPregunta(ctx.formParam("tipoPregunta")))
     // TODO: Para que es el param??
     //.param()
     .pregunta(ctx.formParam("pregunta"))
     .required(true)
     .opciones(opciones)
     .build();
    formulario.getPreguntas().add(pregunta);

    repositorio.guardar(pregunta);
    repositorio.actualizar(formulario);
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


  public void setNavBar(HashMap<String, Object> model) {
    model.put("user_type", "admin");
    model.put("esAdmin", true);
  }


  private void setFormulario(HashMap<String, Object> model) {
    List<Formulario> formularios = (List<Formulario>) repositorio.obtenerTodos(Formulario.class);
    List<FormularioDTO> formulariosDtos = new ArrayList<>();
    for (Formulario formulario : formularios) {
      formulariosDtos.add(new FormularioDTO(formulario));
    }

    model.put("formularios", formulariosDtos);
    setNavBar(model);
  }

  private TipoPregunta obtenerTipoPregunta(String tipoPregunta) {
    return switch (tipoPregunta) {
      case "abierta" -> TipoPregunta.TEXT;
      case "multiple" -> TipoPregunta.CHOICE;
      default -> null;
    };
  }
}
