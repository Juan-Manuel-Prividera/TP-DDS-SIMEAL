package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;

public class DonarDineroController {
    public void index(Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar Dinero");
        setNavBar(model, ctx);

        ctx.render("/colaboraciones/donarDinero.hbs", model);

    }

    public void create(Context app) {
        String metodoPago = app.formParam("metodoPago");
        String monto = app.formParam("monto");

        if (metodoPago == null || monto == null) {
            app.render("impostor_among_us.hbs");
            return;
        }


        Integer montoParsed = Integer.parseInt(monto);

        if (montoParsed <= 0) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("popup_title", "La concha de tu madre");
            model.put("popup_message", "Gracias por tu donación de " + monto + " AR$ \uD83D\uDC80");
            setNavBar(model, app);

            app.render("/colaboraciones/donarDinero.hbs", model);
            return;

        }

        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        Colaborador colaborador = (Colaborador) repo.buscarPorId(app.sessionAttribute("colaborador_id"), Colaborador.class);

        DonarDinero donacion = DonarDinero.create(colaborador, LocalDate.now(), montoParsed);
        repo.guardar(donacion);

        // Crear un modelo para pasar a la vista
        HashMap<String, Object> model = new HashMap<>();
        model.put("popup_title", "Donación realizada ❤");
        model.put("popup_message", "Gracias por tu donación de " + monto + " ARS usando " + metodoPago);
        setNavBar(model, app);

        app.render("/colaboraciones/donarDinero.hbs", model);
    }

    private void setNavBar(HashMap<String, Object> model, Context app) {
        model.put("colaboraciones", "seleccionado");
        model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
        if (app.sessionAttribute("user_type") == "HUMANO")
            model.put("esHumano","true");
        else if (app.sessionAttribute("user_type") == "JURIDICO")
            model.put("esJuridico","true");

        model.put("username", app.sessionAttribute("username"));
    }

}

