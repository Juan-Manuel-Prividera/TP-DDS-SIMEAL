package ar.edu.utn.frba.dds.simeal.server.handlers;

import io.javalin.Javalin;

import java.nio.file.AccessDeniedException;

// This determines what happens when a user tries to access a page without being authenticated
public class NotAuthenticatedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(NotAuthenticatedException.class, (e, ctx) -> {
            ctx.status(401);
            ctx.redirect("/login");
        });
    }

}
