package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TipoJuridico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.*;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuariosController {
    Logger logger = Logger.getInstance();

    public void create(Context context){
        // TODO: Chequear que el nombre no exista ya
        // TODO: Usar el pw validator

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
        // -- USUARIO CREADO --

        // -- TRATAMOS DE CREAR EL COLABORADOR --
        Colaborador colaborador = new Colaborador();
        colaborador.setUsuario(user);

        // Vamos con los campos que sí o sí tiene que estar (los campos de la clase colaborador)
        // Algunos dependen del tipo de usuario
        switch (rol.getTipo()){
            case HUMANO -> {
                String nombre = context.formParam("nombre");
                String apellido = context.formParam("apellido");
                String tipoDocumentoString = context.formParam("tipo_documento");
                String numeroDocumento = context.formParam("numero_documento");

                // Si es humano, t0do esto tiene que estar sí o sí
                if (nombre == null || apellido == null || tipoDocumentoString == null || numeroDocumento == null) {
                    fail(context, "El usuario no proporcionó suficientes datos");
                    return;
                }

                colaborador.setNombre(nombre);
                colaborador.setApellido(apellido);

                TipoDocumento tipoDocumento = TipoDocumento.valueOf(tipoDocumentoString.toUpperCase());
                colaborador.setDocumento(new Documento(tipoDocumento, numeroDocumento));
            }

            case JURIDICO -> {
                String razonSocial = context.formParam("razon_social");
                String rubroString = context.formParam("rubro");
                String tipoJuridicoString = context.formParam("tipo_juridico");

                if (razonSocial == null || rubroString == null || tipoJuridicoString == null) {
                    fail(context, "El usuario no proporcionó suficientes datos");
                    return;
                }

                // TODO: Set rubro
                colaborador.setRazonSocial(razonSocial);
                TipoJuridico tipoJuridico = TipoJuridico.valueOf(tipoJuridicoString.toUpperCase());
                colaborador.setTipoJuridico(tipoJuridico);
            }
        }

        // Otros, no dependen
        String calleNombre = context.formParam("calle_nombre");
        String calleAltura = context.formParam("calle_altura");
        String infoContacto = context.formParam("info_contacto");
        String medioContacto = context.formParam("medio_contacto");

        if (calleNombre == null || calleAltura == null || infoContacto == null || medioContacto == null) {
            fail(context, "El usuario no dio suficientes datos");
            return;
        }

        int altura = Integer.parseInt(calleAltura);
        Ubicacion ubicacion = new Ubicacion(calleNombre, altura);


        // TODO: Setear medio de contacto
        Contacto contacto = new Contacto();
        contacto.setInfoDeContacto(infoContacto);
        /*
        switch (infoContacto) {
            case "email" -> contacto.setMedioContacto(new Email(new EnviadorDeMails()));
        }
         */

        // Ahora, vamos con las preguntas dinámicas
        // Obtenemos el formulario activo
        List<Formulario> formularios = (List<Formulario>) repo.obtenerTodos(Formulario.class);
        Formulario formulario = null;
        for (Formulario f: formularios) {
            if (f.getRol().equals(rol.getTipo()) && f.getEnUso()) {
                formulario = f;
                break;
            }
        }

        // Si no encontramos ningún formulario -> error
        if (formulario == null) {
            context.render("error.hbs");
            return;
        }

        List<Pregunta> preguntas = formulario.getPreguntas();
        // Si el formulario no tiene preguntas -> error
        if (preguntas == null) {
            context.render("error.hbs");
            return;
        }

        // Empezamos a cargar el formulario contestado
        FormularioContestado formularioContestado = new FormularioContestado();
        var formParamMap = context.formParamMap();
        formularioContestado.setFechaRespuesta(LocalDate.now());
        List<Respuesta> respuestas = new ArrayList<>();

        // Por cada pregunta en el formulario activo
        for (Pregunta p : formulario.getPreguntas()){
            // Si no está el parametro en el map del form -> nos cagaron
            if (!formParamMap.containsKey(p.getParam())){
                fail(context, "Pregunta no encontrado: '" + p.getParam() + "'");
                return;
            }

            Respuesta respuesta = new Respuesta();
            respuesta.setPregunta(p);

            String answer = formParamMap.get(p.getParam()).get(0);

            // En función del tipo de pregunta
            switch (p.getTipo()){
                case NUMERICA:
                case TEXT:
                    respuesta.setRespuesta(answer);
                    respuesta.setOpcion(null);
                    break;
                case CHOICE:
                    // Por cada opción disponible en la pregunta
                    for (Opcion o : p.getOpciones()){
                        // Si es la opción que nos llegó
                        if (o.getNombre().equals(answer)){
                            respuesta.setOpcion(o);
                            break;
                        }
                    }
                    break;
                    default:
                        fail(context, "Tipo de pregunta no encontrado: '" + p.getParam() + "'");
                        break;

            } // End switch tipo pregunta
            respuestas.add(respuesta);
        } // End for pregunta in cuestionario
        formularioContestado.setRespuestas(respuestas);
        colaborador.setFormularioContestado(formularioContestado);

        // Si fue t0do bien, persistimos el usr y el colaborador y lo mandamos al login
        repo.guardar(user);
        repo.guardar(colaborador);

        context.redirect("/");

    }

    private void fail(Context context, String reason){
        logger.log(LoggerType.DEBUG, "No se pudo crear el usuario - " + reason);
        logger.log(LoggerType.INFORMATION, "La ip '" + context.ip() + "' está toqueteando el sistema, mandemos la policía");
        context.status(418);
        context.render("impostor_among_us.hbs");
    }
}

