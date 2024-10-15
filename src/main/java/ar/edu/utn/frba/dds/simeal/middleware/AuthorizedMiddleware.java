package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthorizedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.Javalin;

public class AuthorizedMiddleware {
    // Algunos endpoints están abiertos a todos.
    private static final String[] openEndpoints = new String[]{
            "/login", "/", "/index", "/logout",
            "/registro",
            "/registro/humano",
            "/registro/juridico",
            "/user/create/humano",
            "/user/create/juridico",
    };

    private static final String[] openFormats = new String[]{".css", ".jpg", ".png", ".js", ".hbs", ".map", ".gif"};

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
                    // TODO : Arreglar
                    if(ctx.path().startsWith("/heladera") ||
                      ctx.path().startsWith("/suscripcion/") ||
                      ctx.path().startsWith("/suscripciones") ||
                      ctx.path().startsWith("/heladera/suscribirse/") ||
                      ctx.path().startsWith("/setuser/")) {
                      return;
                    }

                    Long userID = ctx.sessionAttribute("user_id");
                    if (userID == null) {
                        System.out.println("NO user_id");
                        throw new NotAuthenticatedException();
                    }

                    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
                    Usuario usuario = (Usuario) repositorio.buscarPorId(userID, Usuario.class);

                    Logger logger = Logger.getInstance("AuthorizedMiddleware.log");
                    logger.log(LoggerType.DEBUG, ctx.path());

                    for (Rol r : usuario.getRoles()){
                        System.out.printf("Probando rol");
                        if (r.tienePermisoPara(
                                ctx.path(),
                                ctx.method())) return;
                    }

                    throw new NotAuthorizedException();

                });
    }

}
