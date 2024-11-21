package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

// This determines what happens when a user tries to access a page without being authenticated
public class NotAuthenticatedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(NotAuthenticatedException.class, (e, ctx) -> {
            Logger.warn("La ip '"+ctx.ip()+"' trató de acceder a un recurso sin auntenticarse ("+ctx.url()+").");
            ctx.status(401);
            ctx.redirect("/");
        });
    }

}
