package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;

// Middlewares are like questions, this' question is: are you authenticated?
public class AuthenticatedMiddleware {

    // Si lo mandamos a logearse y para logearse tiene que estar logeado... => O_o
    private static final String[] openEndpoints = new String[]{
            "/login", "/", "/index",
            "/registro",
            "/registro/humano",
            "/registro/juridico",
            "/user/create/humano",
            "/user/create/juridico",
    };
    private static final String[] openFormats = new String[]{".css", ".jpg", ".png", ".js", ".map", ".gif", ".ico"};

    public static void apply(Javalin app) {
        app.beforeMatched(
        ctx -> {
            for (var format : openFormats) {
                if (ctx.path().endsWith(format)) {
                    return;
                }
            }

            Logger.trace("'"+ctx.ip()+"' trató de acceder a " + ctx.path());
            if(ctx.path().startsWith("/heladera") ||
              ctx.path().startsWith("/suscripcion/") ||
              ctx.path().startsWith("/suscripciones") ||
              ctx.path().startsWith("/heladera/suscribirse/") ||
              ctx.path().startsWith("/setuser/") ||
              ctx.path().startsWith("/recomendacion/") ||
              ctx.path().startsWith("/localidades") )
              //ctx.path().startsWith("/formulario/")  )
            {
                return;
            }
            for (var endpoint : openEndpoints) {
                if (ctx.path().equals(endpoint)) {
                    Logger.trace("'"+ctx.path()+"' es un endpoint abierto, no necesita estar autenticado.");
                    return;
                }
            }

            Long userID = ctx.sessionAttribute("user_id");
            Long colaboradorId = ctx.sessionAttribute("colaborador_id");
            Long tecnicoId= ctx.sessionAttribute("tecnico_id");
            String username = ctx.sessionAttribute("user_name");
            String userType = ctx.sessionAttribute("user_type");

            if (userType == null){
                throw new NotAuthenticatedException();
            }

            if (!userType.equals("ADMIN") && !userType.equals("TECNICO")){
                if (userID == null || colaboradorId == null || username == null) {
                    throw new NotAuthenticatedException();
                }
            }
            if (userType.equals("TECNICO")){
                if (userID == null || tecnicoId == null || username == null) {
                    throw new NotAuthenticatedException();
                }
            }
            Logger.trace("'"+ctx.ip()+"' está autenticado");

            });
    }
}
