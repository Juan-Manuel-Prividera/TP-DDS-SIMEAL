package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.Motivo;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import org.eclipse.emf.ecore.util.EContentAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DistribuirViandaController {
    private Repositorio repositorio;
    private ViandaRepository viandaRepository;

    public DistribuirViandaController (Repositorio repositorio, ViandaRepository viandaRepository) {
        this.repositorio = repositorio;
        this.viandaRepository = viandaRepository;
    }
    public void index(Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("titulo", "Distribuir Vianda");
        setNavBar(model, ctx);

        ctx.render("/colaboraciones/distribuirVianda.hbs", model);
    }

    public void create(Context ctx) {
        if (ctx.formParam("origen_id") == null || ctx.formParam("destino_id") == null || ctx.formParam("motivo") == null) {
            Logger.warn("Impostor among us, halp!");
            ctx.render("impostor_among_us.hbs");
            return;
        }

        Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
        Heladera origen = (Heladera) repositorio.buscarPorId(Long.valueOf(ctx.formParam("origen_id")), Heladera.class);
        Heladera destino = (Heladera) repositorio.buscarPorId(Long.valueOf(ctx.formParam("destino_id")), Heladera.class);
        Motivo motivo = Motivo.valueOf(ctx.formParam("motivo"));

        DistribuirVianda distribuirVianda = DistribuirVianda.builder()
                .cantidadViandasMover(1)
                .origen(origen)
                .destino(destino)
                .motivo(motivo)
                .colaborador(colaborador)
                .fechaDeRealizacion(LocalDate.now())
                .realizada(false)
                .build();

        Vianda viandaADistribuir;
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
