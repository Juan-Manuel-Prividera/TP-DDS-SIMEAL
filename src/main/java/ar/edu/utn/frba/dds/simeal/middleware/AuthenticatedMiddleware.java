package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;

import java.util.regex.Pattern;

// Middlewares are like questions, this' question is: are you authenticated?
public class AuthenticatedMiddleware {
    private static final Pattern[] openEndpoints = new Pattern[]{
            Pattern.compile("/"),
            Pattern.compile("/(index|login)"),
            Pattern.compile("/registro(/(humano|juridico))?"),
            Pattern.compile("/user/create.*"),
            Pattern.compile("/heladeras"),
            Pattern.compile(".*\\.(css|jpg|png|js|map|gif|ico)"),
            Pattern.compile("/localidades.*")
    };

    public static boolean isOpenEndpoint(String input) {
        for (Pattern pattern : openEndpoints) {
            if (pattern.matcher(input).matches()) {
                return true;
            }
        }
        return false;
    }

    public static void apply(Javalin app) {
        app.beforeMatched(
        ctx -> {
            Logger.trace("'"+ctx.ip()+"' trató de acceder a " + ctx.path());
            /*
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
             */
            if (isOpenEndpoint(ctx.path())){
                Logger.trace("'"+ctx.path()+"' es un endpoint abierto, no necesita estar autenticado.");
                return;
            }

            Long userID = ctx.sessionAttribute("user_id");
            String username = ctx.sessionAttribute("user_name");
            String userType = ctx.sessionAttribute("user_type");
            Long colaboradorId = ctx.sessionAttribute("colaborador_id");
            Long tecnicoId= ctx.sessionAttribute("tecnico_id");

            if (userType == null){
                ctx.sessionAttribute("redirect", ctx.path());
                Logger.trace("Redirect set");
                throw new NotAuthenticatedException();
            }

            if (!userType.equals("ADMIN") && !userType.equals("TECNICO")){
                if (userID == null || colaboradorId == null || username == null) {
                    ctx.sessionAttribute("redirect", ctx.path());
                    Logger.trace("Redirect set");
                    throw new NotAuthenticatedException();
                }
            }
            if (userType.equals("TECNICO")){
                if (userID == null || tecnicoId == null || username == null) {
                    ctx.sessionAttribute("redirect", ctx.path());
                    Logger.trace("Redirect set");
                    throw new NotAuthenticatedException();
                }
            }
            Logger.trace("'"+ctx.ip()+"' está autenticado");

            });
    }

}
