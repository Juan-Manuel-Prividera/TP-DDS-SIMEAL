package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.TipoDeComida;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ViandaController {
    public void create(Context context){
        if (!checkInput(context)) {
            context.status(420);
            context.render("impostor_among_us.hbs");
        }

        try {
            TipoDeComida tipoDeComida = new TipoDeComida(context.formParam("tipo_comida"));
            LocalDate fechaCaducidad = LocalDate.parse(context.formParam("fecha_caducidad"));
            Long colaboradorId = context.sessionAttribute("colaborador_id");
            Integer calorias = Integer.parseInt(context.formParam("calorias"));
            Long heladeraId = Long.parseLong(context.formParam("id_heladera"));

            Repositorio repositorio = new Repositorio();
            Vianda vianda = new Vianda(tipoDeComida,
                    fechaCaducidad,
                    LocalDate.now(),
                    (Colaborador) repositorio.buscarPorId(colaboradorId, Colaborador.class),
                    calorias,
                    (Heladera) repositorio.buscarPorId(heladeraId, Heladera.class),
                    false);
            repositorio.guardar(vianda);

            HashMap<String, Object> model = new HashMap<>();
            model.put("popup_title", "Gracias! ❤\uFE0F");
            model.put("popup_message", "Vianda donada con éxito");
            model.put("popup_ruta", "/colaboraciones");

            context.render("/colaboraciones/donarVianda.hbs", model);

        } catch (Exception e) {
            Logger.error("No se pudo crear una vianda - "+e.getMessage());
            HashMap<String, Object> model = new HashMap<>();
            setNavBar(model, context);
            model.put("popup_title", "Error");
            model.put("popup_message", "No se pudo crear una vianda");
            model.put("popup_button", "Reintentar");

            context.render("/colaboraciones/donarVianda.hbs", model);

        }
    }

    private boolean checkInput(Context context) {
        return context.formParam("tipo_comida") == null ||
                context.formParam("fecha_caducidad") == null ||
                context.formParam("calorias") == null ||
                context.formParam("heladera_id") == null;
    }

    private void setNavBar(HashMap<String, Object> model, Context ctx) {
        model.put("tarjetas", "seleccionado");
        model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());
        if (ctx.sessionAttribute("user_type") == "HUMANO")
            model.put("esHumano","true");

        model.put("username", ctx.sessionAttribute("user_name"));
    }

}
