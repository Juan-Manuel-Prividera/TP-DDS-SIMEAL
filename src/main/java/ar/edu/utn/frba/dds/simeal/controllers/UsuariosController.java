package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordGenerator;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.Condicion;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.LongitudTest;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.NoEnBlackList;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.PasswordValidator;
import io.javalin.http.Context;

import java.util.List;

public class UsuariosController {
    private ColaboradorController colaboradorController;
    private Repositorio repo;
    public UsuariosController(ColaboradorController cc, Repositorio repo) {
        this.colaboradorController = cc;
        this.repo = repo;
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

        validarInputs(username,password,passwordRepeat,context);

        List<Condicion> condiciones = List.of(
          new NoEnBlackList("src/main/resources/blacklist.txt"),
          new LongitudTest(8));
        PasswordValidator validator = new PasswordValidator(condiciones);
        String msg = validator.validate(password);
        if (msg != null) {
            context.redirect("/registro/" + rolParam+"?error="+msg);
            return;
        }

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

    public Usuario crearUserTecnico(Tecnico tecnico) {
        List<Rol> roles = (List<Rol>) repo.obtenerTodos(Rol.class);
        Rol tecnicoRol = null;
        for (Rol rol : roles)
            if (rol.getTipo().equals(TipoRol.TECNICO)) {
                tecnicoRol = rol;
                break;
            }

        String password = PasswordGenerator.generar(10);
        Usuario user = new Usuario(
          tecnico.getNombre() + tecnico.getApellido(),
                   PasswordHasher.hashPassword(password),
                   List.of(tecnicoRol)
        );
        
        Logger.debug("Usuario de tecnico creado: <" + user.getUsername()+ " :"+password+ ">");

        Notificador.notificar(tecnico,new Mensaje("Bienvenido a Simeal " + tecnico.getNombre() + "!! \nSus credenciales de acceso al sistema Simeal son: " +
          "\nUsuario: "+ user.getUsername() +  "\n Contraseña: "+ password + "\n" +
          "No comparta estas credenciales. Va a poder modficarlas por unas que usted desee desde nuestro sistema en el apartado de su perfil.\n" +
          "Saludos cordiales.\nSimeal.", "Credenciales de acceso Simeal"));
        Logger.debug("Notificacion enviada a tecnico: " + user.getUsername());
        return user;
    }

    private void validarInputs(String username,String password, String passwordRepeat, Context context) {
        if (username==null || password==null || passwordRepeat==null) {
            fail(context, "User or password or password repeat are null");
            return;
        }

        if (!password.equals(passwordRepeat)){
            fail(context, "Passwords do not match");
            return;
        }
    }
    private void fail(Context context, String reason){
        Logger.error("No se pudo crear el usuario - " + reason);
        Logger.info("La ip '" + context.ip() + "' está toqueteando el sistema, mandemos la policía");
        context.status(418);
        context.render("impostor_among_us.hbs");
    }
}

