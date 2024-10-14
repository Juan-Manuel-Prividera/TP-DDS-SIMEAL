package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroHandler {

    private Repositorio repo;
    public RegistroHandler(Repositorio repositorio) {
        repo = repositorio;
    }

    public void handle(Context context) {

        List<Formulario> formularios = (List<Formulario>) repo.obtenerTodos(Formulario.class);
        Formulario formulario = null;
        String rol = context.pathParam("rol");
        for (Formulario f: formularios) {
            if (f.getRol().equals( TipoRol.valueOf(rol.toUpperCase()) ) && f.getEnUso()) {
                formulario = f;
                break;
            }
        }

        if (formulario == null) {
            context.redirect("/");
            return;
        }

        List<PreguntaDTO> preguntas = new ArrayList<>();

        for (Pregunta pregunta : formulario.getPreguntas()) {
            preguntas.add(
                    PreguntaDTO.builder()
                            .campo(pregunta.getPregunta())
                            .param(pregunta.getParam())
                            .type(String.valueOf(pregunta.getTipo()))
                            .required(pregunta.getRequired().toString())
                            .build()
            );
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("preguntas", preguntas);
        if (context.pathParam("rol").equals("humano")){
            map.put("humano", "true");
            map.put("rol", "humano");
        } else if (context.pathParam("rol").equals("juridico")) {
            map.put("juridico", "true");
            map.put("rol", "juridico");
        }

        context.render("registroFormulario.hbs", map);
    }
}
