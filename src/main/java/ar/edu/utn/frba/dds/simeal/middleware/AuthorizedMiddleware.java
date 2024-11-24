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

import java.util.regex.Pattern;

public class AuthorizedMiddleware {
    // Algunos endpoints están abiertos a todos.
    private static final Pattern[] openEndpoints = new Pattern[]{
            Pattern.compile("/"),
            Pattern.compile("/(index|login|logout)"),
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
                    if (isOpenEndpoint(ctx.path())) {
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
