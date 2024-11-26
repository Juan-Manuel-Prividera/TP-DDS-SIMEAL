package ar.edu.utn.frba.dds.simeal.middleware;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Permiso;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoMetodoHttp;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthenticatedException;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotAuthorizedException;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;

public class AuthorizedMiddleware {
    private static final Permiso[] openEndpoints = new Permiso[]{
            new Permiso("/", TipoMetodoHttp.GET),
            new Permiso("/(index|login|logout)", TipoMetodoHttp.GET),
            new Permiso("/login", TipoMetodoHttp.POST),
            new Permiso("/registro(/(humano|juridico))?", TipoMetodoHttp.GET),
            new Permiso("/user/create/(humano|juridico)", TipoMetodoHttp.POST),
            new Permiso("/heladeras", TipoMetodoHttp.GET),
            new Permiso("/heladera/\\d+", TipoMetodoHttp.GET),
            new Permiso(".*\\.(css|jpg|png|js|map|gif|ico)", TipoMetodoHttp.GET),
            new Permiso("/localidades.*", TipoMetodoHttp.GET),
            new Permiso("/suscripciones", TipoMetodoHttp.GET),
            new Permiso("/suscripcion/\\d+", TipoMetodoHttp.DELETE),
            new Permiso("/formulario/\\d+", TipoMetodoHttp.DELETE),
            new Permiso("/ofertas/comprar", TipoMetodoHttp.POST),
            new Permiso("/ofertas/misOfertas/publicar", TipoMetodoHttp.POST),
            new Permiso("/ofertas/misOfertas/\\d+/modificar", TipoMetodoHttp.DELETE),
            new Permiso("/ofertas/misOfertas/\\d+/modificar", TipoMetodoHttp.POST),
            new Permiso("/recomendacion/ubicaciones", TipoMetodoHttp.GET)
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
                    if (isOpenEndpoint(ctx.path(), TipoMetodoHttp.valueOf(String.valueOf(ctx.method())))) {
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
