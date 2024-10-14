package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import io.javalin.Javalin;

// Middlewares are like questions, this' question is: are you authenticated?
public class AuthenticatedMiddleware {

    // Si lo mandamos a logearse y para logearse tiene que estar logeado... => O_o
    private static final String[] openEndpoints = new String[]{"/login", "/", "/index",
            "/registro",
            "/registro/humano",
            "/registro/juridico",
            "/user/create/humano",
            "/user/create/juridico",
    };
    private static final String[] openFormats = new String[]{".css", ".jpg", ".png"};

    public static void apply(Javalin app) {
        app.beforeMatched(
        ctx -> {

            for (var format : openFormats) {
                if (ctx.path().endsWith(format)) {
                    return;
                }
            }

            for (var endpoint : openEndpoints) {
                if (ctx.path().equals(endpoint)) {
                    return;
                }
            }


            Long userID = ctx.sessionAttribute("user_id");
            if (userID == null) {
                throw new NotAuthenticatedException();
            }
            //Rol rol = ctx.sessionAttribute("rol");
        });
    }
}
