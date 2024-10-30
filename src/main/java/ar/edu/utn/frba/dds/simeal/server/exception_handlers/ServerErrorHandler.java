package ar.edu.utn.frba.dds.simeal.server.exception_handlers;


import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;

public class ServerErrorHandler implements IHandler{
    @Override
    public void setHandler(Javalin app) {
        app.exception(Exception.class, (e, ctx) -> {
            Logger.error("Hubo un error - " + e.getMessage());
            e.printStackTrace();
            ctx.status(500);
            ctx.render("error.hbs");
        });

    }
}
