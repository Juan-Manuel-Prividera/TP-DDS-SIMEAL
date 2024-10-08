package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

public class LogoutHandler {
    public void handle(Context context){
        context.req().getSession().invalidate();
        context.redirect("/");
    }
}
