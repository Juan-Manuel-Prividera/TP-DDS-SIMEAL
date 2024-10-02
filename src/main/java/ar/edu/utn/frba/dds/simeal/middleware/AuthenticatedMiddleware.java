package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.server.handlers.NotAuthenticatedException;
import io.javalin.Javalin;

// Middlewares are like questions, this' question is: are you authenticated?
public class AuthenticatedMiddleware {

    // Si lo mandamos a logearse y para logearse tiene que estar logeado... => O_o
    private static final String[] openEndpoints = new String[]{"/login", "/", "/index",
            "/registro",
            "/registro/persona",
            "/registro/juridico"};
    private static final String[] openFormats = new String[]{".css", ".jpg", ".png"};

    public static void apply(Javalin app) {
        app.beforeMatched(ctx -> {

//            for (var format : openFormats) {
//                if (ctx.path().endsWith(format)) {
//                    return;
//                }
//            }
//
//            for (var endpoint : openEndpoints) {
//                if (ctx.path().equals(endpoint)) {
//                    return;
//                }
//            }
//
//
//            Integer userID = ctx.sessionAttribute("userID");
//            if (userID == null) {
//                throw new NotAuthenticatedException();
//            }
//            Rol rol = ctx.sessionAttribute("rol");
        });
    }
}
