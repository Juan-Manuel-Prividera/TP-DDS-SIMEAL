package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import io.javalin.Javalin;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.nio.charset.StandardCharsets;

public class NotFoundHandler implements IHandler {

    @Override
    public void setHandler(Javalin app) {
        app.exception(NotFoundException.class, (e, ctx) -> {
            Logger.warn("La ip '" + ctx.ip() + "' trató de acceder a un recurso inexistente ('"+
                    java.net.URLDecoder.decode(ctx.url(), StandardCharsets.UTF_8)
                    +"').");
            ctx.status(404);
            ctx.render("notFound.hbs");
        });
    }
}
