package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.DDMetricsUtils;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;

public class DonarViandaController {

    public void index(Context app) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar Vianda");
        setNavBar(model, app);

        app.render("/colaboraciones/donarVianda.hbs", model);
    }

    public void create(Colaborador colaborador, Vianda vianda) {
        DonarVianda donarVianda = new DonarVianda(colaborador, LocalDate.now(), vianda);
        Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
        repositorio.guardar(donarVianda);

        DDMetricsUtils.getInstance().getViandasDonadas().incrementAndGet();
    }

    public void setNavBar(HashMap<String, Object> model, Context app) {
        model.put("colaboraciones", "seleccionado");
        model.put("user_type", app.sessionAttribute("user_type").toString().toLowerCase());
        if (app.sessionAttribute("user_type") == "HUMANO")
            model.put("esHumano","true");
        else if (app.sessionAttribute("user_type") == "JURIDICO")
            model.put("esJuridico","true");

        model.put("username", app.sessionAttribute("user_name"));
    }
}
