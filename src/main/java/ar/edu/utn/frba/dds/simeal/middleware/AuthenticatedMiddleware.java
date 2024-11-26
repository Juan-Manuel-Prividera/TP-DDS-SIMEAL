package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.models.usuario.Permiso;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoMetodoHttp;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;

// Middlewares are like questions, this' question is: are you authenticated?
public class AuthenticatedMiddleware {
    private static final Permiso[] openEndpoints = new Permiso[]{
            new Permiso("/", TipoMetodoHttp.GET),
            new Permiso("/(index|login)", TipoMetodoHttp.GET),
            new Permiso("/login", TipoMetodoHttp.POST),
            new Permiso("/registro(/(humano|juridico))?", TipoMetodoHttp.GET),
            new Permiso("/user/create/(humano|juridico)", TipoMetodoHttp.POST),
            new Permiso("/heladeras", TipoMetodoHttp.GET),
            new Permiso(".*\\.(css|jpg|png|js|map|gif|ico)", TipoMetodoHttp.GET),
            new Permiso("/localidades.*", TipoMetodoHttp.GET),
            new Permiso("/suscripciones", TipoMetodoHttp.GET),
            new Permiso("/ofertas/comprar", TipoMetodoHttp.POST),
            new Permiso("/ofertas/misOfertas/publicar", TipoMetodoHttp.POST),
            new Permiso("/ofertas/misOfertas/\\d+/modificar", TipoMetodoHttp.POST),
            new Permiso("/ofertas/misOfertas/\\d+/modificar", TipoMetodoHttp.DELETE)
    };

    public static boolean isOpenEndpoint(String input, TipoMetodoHttp metodo) {
        for (Permiso permiso: openEndpoints) {
            if (permiso.isAllowed(input, metodo)){
                return true;
            }
        }
        return false;
    }

    public static void apply(Javalin app) {
        app.beforeMatched(
        ctx -> {
            Logger.trace("'"+ctx.ip()+"' trató de acceder a " + ctx.path());
            if (isOpenEndpoint(ctx.path(), TipoMetodoHttp.valueOf(String.valueOf(ctx.method())))){
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
