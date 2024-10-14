package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

// This determines what happens when a user tries to access a page without being authenticated
public class NotAuthenticatedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(NotAuthenticatedException.class, (e, ctx) -> {
            HttpSession session = ctx.req().getSession(false);
            if (session != null) {
                session.invalidate();  // Invalidate session
            }
            ctx.status(401);
            ctx.redirect("/");
        });
    }

}
