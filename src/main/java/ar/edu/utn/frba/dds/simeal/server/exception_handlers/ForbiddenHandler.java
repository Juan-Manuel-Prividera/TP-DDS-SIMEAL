package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import io.javalin.Javalin;

import java.nio.file.AccessDeniedException;

// This determines what happens when a user tries to access a page they're not supposed to
public class ForbiddenHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(AccessDeniedException.class, (e, ctx) -> {
            ctx.status(403);
            ctx.render("forbidden.hbs");
        });
    }
}
