package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

import java.util.Map;

public class HumanoHomeHandler {
    public void handle(Context context) {
        context.render("humano_home.hbs", Map.of("username", context.sessionAttribute("user_name")));
    }
}
