package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

public class NotAuthorizedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(NotAuthorizedException.class, (e, ctx) -> {
            ctx.status(403);
            ctx.render("forbidden.hbs");
        });
    }
}
