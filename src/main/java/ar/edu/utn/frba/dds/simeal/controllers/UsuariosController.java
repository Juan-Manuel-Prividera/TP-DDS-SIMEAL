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
        String medioContacto = context.formParam("medio_contacto");
        String infoContacto = context.formParam("info_contacto");
        Provincia provincia = Provincia.valueOf(context.formParam("provincia"));
        String codigoPostal = context.formParam("codigo_postal");

        if (calleNombre == null || calleAltura == null || infoContacto == null || medioContacto == null) {
            fail(context, "El usuario no dio suficientes datos");
            return;
        }

        int altura = Integer.parseInt(calleAltura);
        Ubicacion ubicacion = new Ubicacion(calleNombre, altura, provincia,Integer.parseInt(codigoPostal));
        colaborador.setUbicacion(ubicacion);

        // TODO: Setear medio de contacto
        Contacto contacto = new Contacto();
        contacto.setInfoDeContacto(infoContacto);
        contacto.setMedioContacto(MedioDeContactoFactory.crearMedioDeContactoDeString(medioContacto));
        repo.guardar(contacto);

        colaborador.setContactoPreferido(contacto);
        colaborador.addContacto(contacto);

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
        Map<String, List<String>> formParamMap = context.formParamMap();
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

        FormularioContestado formularioContestado = new FormularioContestado(respuestas, formulario);
        colaborador.setFormularioContestado(formularioContestado);

        //Seteamos tarjeta de colaborador
        TarjetaColaborador tarjetaColaborador = new TarjetaColaborador(colaborador, LocalDate.now());

        // Si fue t0do bien, persistimos el usr y el colaborador y lo mandamos al login
        repo.guardar(colaborador);

        ServiceLocator.getRepository(TarjetaColaboradorRepository.class).guardar(tarjetaColaborador);

        Logger.info("Usuario creado correctamente");
        context.redirect("/");

    }

    private void fail(Context context, String reason){
        Logger.error("No se pudo crear el usuario - " + reason);
        Logger.info("La ip '" + context.ip() + "' está toqueteando el sistema, mandemos la policía");
        context.status(418);
        context.render("impostor_among_us.hbs");
    }
}

