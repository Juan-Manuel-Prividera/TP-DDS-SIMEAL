package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.http.Context;

public class UsuariosController {
    Logger logger = Logger.getInstance();

    public void create(Context context){
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

        Usuario user = new Usuario(username, PasswordHasher.hashPassword(password), null);
        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
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

