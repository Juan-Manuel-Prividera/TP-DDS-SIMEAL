package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.http.Context;

import java.util.List;


public class LoginHandler {

    public void handle(Context context) {
        Logger logger = Logger.getInstance("login.log");

        String username = context.formParam("user");
        String password = context.formParam("password");

        if (username == null || password == null) {
            // The user bypassed the frontend and sent no username and/or no password
            logger.log(LoggerType.DEBUG, "Someone tried to login without a username or password");
            context.render("impostor_among_us.hbs");
            return;
        }

        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        List<Usuario> usuarios = (List<Usuario>) repo.obtenerTodos(Usuario.class);
        Usuario usuario = null;

        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            logger.log(LoggerType.DEBUG, "The requested user does not exist");
            fail(context);
            return;
        }

        if (!PasswordHasher.checkPassword(password, usuario.getHash())) {
            logger.log(LoggerType.DEBUG, "Incorrect login attempt");
            fail(context);
        } else {
            logger.log(LoggerType.INFORMATION, "Login successful, welcome " + username);

            // TODO: Setear Cookies:
            //  - el id_usuario, nombre_usuario, rol, colaborador_id (hacer metodo), ...

            //context.sessionAttribute("user_id", usuario.getId());

            Long colaboradorID = null;

            List<Colaborador> colaboradores = (List<Colaborador>) repo.obtenerTodos(Colaborador.class);
            for (Colaborador c : colaboradores){
                Usuario u = c.getUsuario();
                if (u == null) continue;
                if (c.getUsuario().equals(usuario)){
                    colaboradorID = c.getId();
                    break;
                }
            }

            if (colaboradorID == null) {
                logger.log(LoggerType.DEBUG, "No hay un colaborador asociado al usuario '"+username+"'");
            } else {
                context.sessionAttribute("colaborador_id", colaboradorID);
            }

            // Guardamos los datos que necesitemos en la sesión
            context.sessionAttribute("user_id", usuario.getId());
            context.sessionAttribute("user_name", usuario.getUsername());

            for (Rol r : usuario.getRoles())
                switch (r.getTipo()) {
                case ADMIN:
                    context.sessionAttribute("user_type", r.getTipo().toString());
                    context.redirect("/formularios");
                    break;

                case JURIDICO:
                    context.sessionAttribute("user_type", r.getTipo().toString());
                    context.redirect("/home");
                    break;

                case HUMANO:
                    context.sessionAttribute("user_type", r.getTipo().toString());
                    context.redirect("/home");
                    break;

                default:
                    context.result("xd");
                    break;
                }
        }
    }

    private void fail(Context context){
        context.status(401);
        context.redirect("/?failed=true");
    }

}
