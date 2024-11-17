package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthorizedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import com.mysql.cj.log.Log;
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

    private static final String[] openFormats = new String[]{".css", ".jpg", ".png", ".js", ".hbs", ".map", ".gif", ".ico"};

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
                      ctx.path().startsWith("/setuser/") ||
                      ctx.path().startsWith("/formulario/") ||
                      ctx.path().startsWith("/ofertas/") ||
                      ctx.path().startsWith("/recomendacion/") ||
                      ctx.path().startsWith("/localidades"))
                     {
                      return;
                    }

                    Long userID = ctx.sessionAttribute("user_id");
                    if (userID == null) {
                        Logger.error("El usuario no tiene un id asociado => no está autorizado para nada");
                        throw new NotAuthenticatedException();
                    }

                    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
                    Usuario usuario = (Usuario) repositorio.buscarPorId(userID, Usuario.class);

                    for (Rol r : usuario.getRoles()){
                        if (r.tienePermisoPara(
                                ctx.path(),
                                ctx.method())) {
                            Logger.trace("'" + ctx.ip() + "' está autorizado para acceder a ", ctx.path());
                            return;
                        }
                    }

                    throw new NotAuthorizedException();


                });
    }

}
