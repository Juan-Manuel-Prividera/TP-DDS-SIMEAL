package ar.edu.utn.frba.dds.simeal.server.exception_handlers;


import io.javalin.Javalin;

public class ServerErrorHandler implements IHandler{
    @Override
    public void setHandler(Javalin app) {
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.render("error.hbs");
        });

    }
}
