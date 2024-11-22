package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DistribuirViandaController {
    public void index(Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("titulo", "Distribuir Vianda");
        setNavBar(model, ctx);

        ctx.render("/colaboraciones/distribuirVianda.hbs", model);
    }

    public void create(Context ctx) {
        if (ctx.formParam("origen_id") == null || ctx.formParam("destino_id") == null) {
            Logger.warn("Impostor among us, halp!");
            ctx.render("impostor_among_us.hbs");
            return;
        }

        Repositorio repositorio = new Repositorio();
        Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
        Heladera origen = (Heladera) repositorio.buscarPorId(Long.valueOf(ctx.formParam("origen_id")), Heladera.class);
        Heladera destino = (Heladera) repositorio.buscarPorId(Long.valueOf(ctx.formParam("destino_id")), Heladera.class);

        DistribuirVianda distribuirVianda = DistribuirVianda.builder()
                .cantidadViandasMover(1)
                .origen(origen)
                .destino(destino)
                .colaborador(colaborador)
                .fechaDeRealizacion(LocalDate.now())
                .build();

        Vianda viandaADistribuir;
        ViandaRepository viandaRepository = new ViandaRepository();
        List<Vianda> viandas = viandaRepository.buscarPorHeladera(origen);
        if (viandas.isEmpty()) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("popup_title", "No hay viandas en esa heladera!");
            model.put("popup_message", "En este momento, no hay ninguna vianda para distribuir de esta heladera :/");
            setNavBar(model, ctx);
            ctx.render("/colaboraciones/distribuirVianda.hbs", model);
            return;
        }

        viandaADistribuir = viandas.get(0);
        viandaADistribuir.setHeladera(destino);
        repositorio.guardar(distribuirVianda);

        HashMap<String, Object> model = new HashMap<>();
        model.put("popup_title", "Gracias por tu colaboración ❤");
        model.put("popup_message", "En unos días te va a llegar un email con los detalles de la distribución");
        setNavBar(model, ctx);

        ctx.render("/colaboraciones/distribuirVianda.hbs", model);

    }

    private void setNavBar(HashMap<String, Object> model, Context app) {
        model.put("colaboraciones", "seleccionado");
        model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
        if (app.sessionAttribute("user_type") == "HUMANO")
            model.put("esHumano","true");
        else if (app.sessionAttribute("user_type") == "JURIDICO")
            model.put("esJuridico","true");

        model.put("username", app.sessionAttribute("user_name"));
    }
}
