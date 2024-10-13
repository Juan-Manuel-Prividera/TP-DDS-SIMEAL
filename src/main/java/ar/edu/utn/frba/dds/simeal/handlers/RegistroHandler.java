package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistroHandler {
    public void handle(Context context) {

        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        //List<Formulario> formularios = (List<Formulario>) repo.obtenerTodos(Formulario.class);
        Formulario formularioActivo = null;

        /*for (Formulario form : formularios) {
            if (form.getActivo()) {
                formularioActivo = form;
            }
        }
        *
         */

        //if (formularioActivo == null) formularioActivo = formularios.get(0);
        if (formularioActivo == null) {
            formularioActivo = new Formulario();
            formularioActivo.setPreguntas(new ArrayList<>());
        }

        List<PreguntaDTO> preguntas = new ArrayList<>();

        for (Pregunta pregunta : formularioActivo.getPreguntas()) {
            preguntas.add(
                    PreguntaDTO.builder()
                            .campo(pregunta.getPregunta())
                            .param(pregunta.getParam())
                            .type(String.valueOf(pregunta.getTipo()))
                            .required(pregunta.getRequired().toString())
                            .build()
            );
        }

        // TODO: Humano o persona?? usemosuno solo!!
        HashMap<String, Object> map = new HashMap<>();
        map.put("preguntas", preguntas);
        if (context.pathParam("rol").equals("persona")){
            map.put("persona", "true");
            map.put("rol", "humano");
        } else if (context.pathParam("rol").equals("juridico")) {
            map.put("juridico", "true");
            map.put("rol", "juridico");
        }

        context.render("registroFormulario.hbs", map);
    }
}
