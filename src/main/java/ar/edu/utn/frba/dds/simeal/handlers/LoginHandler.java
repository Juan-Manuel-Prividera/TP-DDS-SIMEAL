package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

public class LoginHandler {
    public void handle(Context context) {
        context.render("login.hbs");
    }
}
