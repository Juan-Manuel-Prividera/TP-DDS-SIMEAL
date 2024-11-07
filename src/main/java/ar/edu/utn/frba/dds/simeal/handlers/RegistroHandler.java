package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.OpcionDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.formulario.PreguntaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            Logger.error("No existe el formulario con el rol " + rol);
            context.render("error.hbs");
            return;
        }

        List<PreguntaDTO> preguntas = new ArrayList<>();
        for (Pregunta pregunta : formulario.getPreguntas()) {

            List<Opcion> opciones = pregunta.getOpciones();
            List<OpcionDTO> opcionesDTOS = new ArrayList<>();
            Boolean isChoice = false;
            if (!opciones.isEmpty()){
                isChoice = true;
                for (Opcion o : opciones)
                    opcionesDTOS.add(new OpcionDTO(o.getNombre()));
            }

            PreguntaDTO preguntaDTO = new PreguntaDTO(pregunta);
            preguntas.add(preguntaDTO);
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

        // TODO: Revisar como pasar esta info de alguna otra forma que no sea query param
        if (context.queryParam("error") != null) {
            map.put("error", context.queryParam("error"));
            map.put("popup_message", context.queryParam("error"));
            map.put("popup_title", "Error");
            map.put("popup_button", "Reintentar");
        }

        Logger.trace("Saliendo de registro handler");
        context.render("registroFormulario.hbs", map);
    }
}
