package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.TipoDeComida;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.time.LocalDate;

public class ViandaController {
    public void create(Context context){
        if (!checkInput(context)) {
            context.status(420);
            context.render("impostor_among_us.hbs");
        }

        TipoDeComida tipoDeComida = new TipoDeComida(context.formParam("tipo_comida"));
        LocalDate fechaCaducidad = LocalDate.parse(context.formParam("fecha_caducidad"));
        Long colaboradorId = Long.parseLong(context.formParam("colaborador_id"));
        Integer calorias = Integer.parseInt(context.formParam("calorias"));
        Long heladeraId = Long.parseLong(context.formParam("heladera_id"));

        Repositorio repositorio = new Repositorio();
        Vianda vianda = new Vianda(tipoDeComida,
                fechaCaducidad,
                LocalDate.now(),
                (Colaborador) repositorio.buscarPorId(colaboradorId, Colaborador.class),
                calorias,
                (Heladera) repositorio.buscarPorId(heladeraId, Heladera.class),
                false);
        repositorio.guardar(vianda);

        //render
    }

    private boolean checkInput(Context context) {
        return context.formParam("tipo_comida") == null ||
                context.formParam("fecha_caducidad") == null ||
                context.formParam("calorias") == null ||
                context.formParam("heladera_nombre") == null;
    }
}
