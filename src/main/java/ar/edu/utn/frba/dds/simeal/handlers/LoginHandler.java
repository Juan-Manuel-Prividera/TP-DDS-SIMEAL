package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import io.javalin.http.Context;

import java.util.List;


public class LoginHandler {

    public void handle(Context context) {

        String username = context.formParam("user");
        String password = context.formParam("password");

        if (username == null || password == null) {
            // The user bypassed the frontend and sent no username and/or no password
            context.render("impostor_among_us.hbs");
            return;
        }

        Repositorio repoUsuarios = ServiceLocator.getRepository(Repositorio.class);
        List<Usuario> usuarios = (List<Usuario>) repoUsuarios.obtenerTodos(Usuario.class);
        Usuario usuario = null;

        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            fail(context);
            return;
        }

        if (!PasswordHasher.checkPassword(password, usuario.getHash())) {
            // TODO: Que no pueda forzar la contraseña !!
            // Invalid credentials, return an error TODO: Retornar lo mismo que linea 39
            fail(context);
        } else {

            // TODO: Setear Cookies:
            //  - el id_usuario, nombre_usuario, rol, colaborador_id (hacer metodo), ...

            //:w
            //context.sessionAttribute("user_id", usuario.getId());

            for (Rol r : usuario.getRoles())
                switch (r.getTipo()) {
                case ADMIN:
                    context.sessionAttribute("tipo_usuario", r.getTipo().toString());
                    context.redirect("/admin/");
                    break;

                case JURIDICO:
                    // TODO: Guardar cookies que necesite el resto
                    context.sessionAttribute("tipo_usuario", r.getTipo().toString());
                    context.redirect("/home/juridico/");
                    break;

                case HUMANO:
                    // TODO: Guardar cookies que necesite el resto
                    context.sessionAttribute("tipo_usuario", r.getTipo().toString());
                    context.redirect("/home/humano");
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
