package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TipoJuridico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.*;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.Condicion;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.LongitudTest;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.NoEnBlackList;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.PasswordValidator;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuariosController {
    private ColaboradorController colaboradorController;

    public UsuariosController(ColaboradorController cc) {
        colaboradorController = cc;
    }

    public void create(Context context){
        // TODO: Chequear que el nombre no exista ya
        Logger.info("Creando usuario");
        // -- TRATAMOS DE CREAR EL USUARIO --
        // Estos son los básicos que necesitamos, lo demás en negociable
        String rolParam = context.pathParam("rol");
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

        // TODO: Hacer esto un toque mejor
        ArrayList<Condicion> condiciones = new ArrayList<>();
        // TODO: Qué ruta pongo para la blacklist??
        condiciones.add(new NoEnBlackList("src/main/resources/blacklist.txt"));
        condiciones.add(new LongitudTest(8));
        PasswordValidator validator = new PasswordValidator(condiciones);
        String msg = validator.validate(password);
        if (msg != null) {
            context.redirect("/registro/" + rolParam+"?error="+msg);
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

        if (rol == null) {
            fail(context, "Rol no encontrado: '" + rolParam + "'");
            return;
        }

        Usuario user = new Usuario(username, PasswordHasher.hashPassword(password), List.of(rol));
        Logger.info("Usuario creado (no persistido)");

        colaboradorController.create(user, context);
    }

    private void fail(Context context, String reason){
        Logger.error("No se pudo crear el usuario - " + reason);
        Logger.info("La ip '" + context.ip() + "' está toqueteando el sistema, mandemos la policía");
        context.status(418);
        context.render("impostor_among_us.hbs");
    }
}

