package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.http.Context;

import java.util.List;

public class UsuariosController {
    Logger logger = Logger.getInstance();

    public void create(Context context){

        // TODO: Chequear que llegue
        // TODO: CHequear que el nombre no exista ya
        String rolParam = context.pathParam("rol");

        // Estos son los básicos que necesitamos, lo demás en negociable
        String username = context.formParam("user");
        String password = context.formParam("password");
        String passwordRepeat = context.formParam("passwordRepeat");

        if (username==null || password==null || passwordRepeat==null) {
            fail(context, "User or password or password repeat are null");
            return;
        }

        if (!password.equals(passwordRepeat)){
            fail(context, "Passwords do not match");
            return;
        }


        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        List<Rol> roles = (List<Rol>) repo.obtenerTodos(Rol.class);
        Rol rol = null;
        for (Rol r : roles)
            if (r.getTipo().equals(TipoRol.valueOf(rolParam.toUpperCase()))) {
                rol = r;
                break;
            }

        Usuario user = new Usuario(username, PasswordHasher.hashPassword(password), List.of(rol));
        repo.guardar(user);

        // Si fue todo bien lo mandamos al login
        context.redirect("/");

    }

    private void fail(Context context, String reason){
        logger.log(LoggerType.DEBUG, "No se pudo crear el usuario - " + reason);
        logger.log(LoggerType.INFORMATION, "La ip '" + context.ip() + "' está toqueteando el sistema, mandemos la policía");
        context.status(418);
        context.render("impostor_among_us.hbs");
    }
}

