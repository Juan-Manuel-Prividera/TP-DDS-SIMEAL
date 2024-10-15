package ar.edu.utn.frba.dds.simeal;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.WhatsApp;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.TipoDeComida;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.ModeloHeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.simeal.models.usuario.*;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

// Este archivo mete en la BD algunos datos hardcodeados, como los tipos de roles, los formularios, etc...
public class InitPersistence {
    public static void main(String[] args){
        initPermisosRolesYUsuarios();
        initFormularios();
        initHeladeras();

    }

    private static void initPermisosRolesYUsuarios(){
        // Creamos los permisos
        // Homepage
        Permiso getHome = new Permiso("home", TipoMetodoHttp.GET);

        // Admin
        Permiso getMigracion = new Permiso("migracion", TipoMetodoHttp.GET);
        Permiso postMigracionUpload = new Permiso("migracion/upload", TipoMetodoHttp.POST);
        Permiso getReportes = new Permiso("reportes", TipoMetodoHttp.GET);
        Permiso getCambiarModo = new Permiso("cambiarmodo", TipoMetodoHttp.GET);

        Permiso getFormularios = new Permiso("formularios", TipoMetodoHttp.GET);
        Permiso postFormularios = new Permiso("formulario", TipoMetodoHttp.POST);
        Permiso getFormulario = new Permiso("formulario/.+", TipoMetodoHttp.GET);
        Permiso postPregunta = new Permiso("formulario/\\d+/pregunta", TipoMetodoHttp.POST);

        Permiso deletePregunta = new Permiso("/formularios/\\d+/pregunta/\\d+", TipoMetodoHttp.DELETE);
        Permiso deleteFormulario = new Permiso("/formularios/\\d+", TipoMetodoHttp.DELETE);

        // Humano
        Permiso getTarjeta = new Permiso("/tarjeta", TipoMetodoHttp.GET);
        Permiso getTarjetas = new Permiso("/tarjeta/.+",TipoMetodoHttp.GET);
        Permiso postTarjetas = new Permiso("/tarjeta/.+",TipoMetodoHttp.POST);
        Permiso getSolicitud = new Permiso("/solicitud/.+", TipoMetodoHttp.GET);
        Permiso postSolicitud = new Permiso("/solicitud/.+", TipoMetodoHttp.POST);



        Permiso getColaboraciones = new Permiso("colaboraciones", TipoMetodoHttp.GET);
        Permiso postDonarDinero = new Permiso("colaboraciones/donarDinero", TipoMetodoHttp.POST);
        Permiso getDonarDinero = new Permiso("colaboraciones/donarDinero", TipoMetodoHttp.GET);
        Permiso getDonarVianda = new Permiso("colaboraciones/donarVianda", TipoMetodoHttp.GET);
        Permiso postDonarVianda = new Permiso("colaboraciones/donarVianda", TipoMetodoHttp.POST);
        Permiso getAdherirHeladera = new Permiso("colaboraciones/adherirHeladera", TipoMetodoHttp.GET);
        Permiso postAdherirHeladera = new Permiso("colaboraciones/adherirHeladera", TipoMetodoHttp.POST);

        Permiso getHeladera = new Permiso("heladera", TipoMetodoHttp.GET);
        Permiso getHeladeraEspecifico = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.GET);
        Permiso postHeladera = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.POST);

        Permiso getSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);
        Permiso postSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);
        Permiso getSuscripciones = new Permiso("/suscripciones/\\d+", TipoMetodoHttp.GET);
        Permiso deleteSuscripciones = new Permiso("/suscripcion/\\d+", TipoMetodoHttp.DELETE);

        Permiso getRecomendacion = new Permiso("/recomendacion", TipoMetodoHttp.GET);

        Permiso getHeladeras = new Permiso("heladeras", TipoMetodoHttp.GET);
        Permiso getOfertas = new Permiso("ofertas", TipoMetodoHttp.GET);
        Permiso getOferta = new Permiso("oferta/\\d+", TipoMetodoHttp.GET);

        // Crear roles
        List<Permiso> permisosHumano = List.of(
                getHome, getTarjeta, getTarjetas, postTarjetas, postDonarDinero, getColaboraciones,
                getHeladera, getHeladeraEspecifico, postHeladera, getSuscribirHeladera, postSuscribirHeladera,
                getHeladeras, getOfertas, getOferta, getSuscripciones, deleteSuscripciones,
                getSolicitud, postSolicitud, getDonarDinero, postDonarVianda, postDonarDinero, getDonarVianda
          );
        Rol humano = new Rol(TipoRol.HUMANO, permisosHumano);

        List<Permiso> permisosJuridico = List.of(
                getHome, getHeladera, postHeladera, postDonarDinero, getColaboraciones,
                getHeladeras, getOfertas, getOferta, getDonarDinero, postDonarDinero,
                getAdherirHeladera, postAdherirHeladera, getRecomendacion
        );
        Rol juridico = new Rol(TipoRol.JURIDICO, permisosJuridico);

        List<Permiso> permisosAdmin = List.of(
                getMigracion, postMigracionUpload, getReportes, getCambiarModo,
                getFormularios, getFormulario, postFormularios, postPregunta,
                deletePregunta, deleteFormulario, getTarjeta, getTarjetas, postTarjetas, getColaboraciones,
                postDonarDinero,
                getHeladeraEspecifico, getHeladera, getHeladeras, postHeladera, getSuscribirHeladera,
                getOferta, getOfertas, postSuscribirHeladera, getSuscripciones, deleteSuscripciones,
                getHome
        );
        Rol admin = new Rol(TipoRol.ADMIN, permisosAdmin);

        Repositorio repo = new Repositorio();
        repo.guardar(humano);
        repo.guardar(juridico);
        repo.guardar(admin);

        Usuario usuarioAdmin = new Usuario("admin", PasswordHasher.hashPassword("admin"), List.of(admin));
        Usuario usuarioHumano = new Usuario("humano", PasswordHasher.hashPassword("humano"), List.of(humano));
        Usuario usuarioJuridico = new Usuario("juridico", PasswordHasher.hashPassword("juridico"), List.of(juridico));

        Colaborador colaboradorHumano = new Colaborador(
                new Documento(TipoDocumento.DNI, "13"),
                "Friederich", "Gauss");
        colaboradorHumano.setUsuario(usuarioHumano);
        repo.guardar(colaboradorHumano);

        TarjetaColaborador tarjetaColaborador = new TarjetaColaborador(colaboradorHumano, LocalDate.now());
        ServiceLocator.getRepository(TarjetaColaboradorRepository.class).guardar(tarjetaColaborador);

        Colaborador colaboradorJuridico = new Colaborador(
                "Arcos dorados",
                new Rubro()
        );

        colaboradorJuridico.setUsuario(usuarioJuridico);
        repo.guardar(colaboradorJuridico);

        repo.guardar(usuarioAdmin);
        repo.guardar(usuarioHumano);
        repo.guardar(usuarioJuridico);

    }
    private static void initFormularios() {
        Repositorio repo = ServiceLocator.getRepository(Repositorio.class);
        // Formulario persona
        Opcion opcionSi = new Opcion("Sí");
        Opcion opcionUnPoco = new Opcion("Un poco");
        Opcion opcionSiMucho = new Opcion("Sí, mucho!");
        Pregunta teGustanLosGatos = new Pregunta("¿Te gustan los gatos?", "gatos_sino",
                TipoPregunta.CHOICE, List.of(opcionUnPoco, opcionSi, opcionSiMucho), true);
        Pregunta comoSeLlamaTuGato = new Pregunta("¿Cómo se llama(n) tu(s) gato(s)?", "gatos_nombres",
                TipoPregunta.TEXT, null, true);
        Formulario formularioHumano = new Formulario(List.of(teGustanLosGatos, comoSeLlamaTuGato), true,
                TipoRol.HUMANO, "Cuestionario de registro humano");
        repo.guardar(formularioHumano);

        // Formulario juridico
        Opcion muyEnDesacuerdo = new Opcion("Muy en desacuerdo");
        Opcion levementeEnDesacuerdo = new Opcion("Levemente en desacuerdo");
        Opcion indiferente = new Opcion("Indiferente");
        Opcion levementeDeAcuerdo = new Opcion("Levemente de acuerdo");
        Opcion muyDeAcuerdo = new Opcion("Muy de acuerdo");
        Pregunta cuanDeAcuerdo = new Pregunta("¿Está de acuerdo con la siguiente afirmación?: 'Vamos a besar gatos'", "cuan_de_acuerdo",
                TipoPregunta.CHOICE, List.of(muyEnDesacuerdo, levementeEnDesacuerdo,
                indiferente, levementeDeAcuerdo, muyDeAcuerdo), true);
        Pregunta porfavorVentajas = new Pregunta("Por favor, indique tres ventajas por las que tener un gato como mascota", "razones",
                TipoPregunta.TEXT, null, true);
        Formulario formularioJuridico = new Formulario(List.of(cuanDeAcuerdo, porfavorVentajas), true,
                TipoRol.JURIDICO, "Cuestionario de registro juridico");
        repo.guardar(formularioJuridico);
    }

    private static void initHeladeras() {
        Ubicacion ubicacion = new Ubicacion("Sarmiento", 2600, -34.57991061741367, -58.42111388432673);
        Ubicacion ubicacion1 = new Ubicacion("Medrano", 1344, -34.59859548850497, -58.42011530932118);
        Ubicacion ubicacion2 = new Ubicacion("Mozart", 2300, -34.65946430376541, -58.46797562733076);
        Ubicacion ubicacion3 = new Ubicacion("Santa Fe", 1860, -34.59266250613197, -58.39405684357533);
        Ubicacion ubicacion4 = new Ubicacion("Gorriti", 900, -34.60679445485131, -58.36548827958108);
        ModeloHeladera modeloHeladera = new ModeloHeladera("Modelo1",2,3,4);
        Repositorio modeloRepo = ServiceLocator.getRepository(ModeloHeladeraRepository.class);
        modeloRepo.guardar(modeloHeladera);

        Colaborador colaborador0 = new Colaborador(new Documento(TipoDocumento.DNI,"12345678"),"Juan","Perez", new Contacto("541198765432", new WhatsApp(null)));
        colaborador0.sumarPuntosReconocimiento(200);

        Repositorio repositorioColabs = ServiceLocator.getRepository(Repositorio.class);
        repositorioColabs.guardar(colaborador0);

        Heladera heladera = new Heladera("Heladera Plaza Italia",ubicacion, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());
        Heladera heladera1 = new Heladera("Heladera Medrano",ubicacion1, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());
        Heladera heladera2 = new Heladera("Heladera Campus",ubicacion2, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());
        Heladera heladera3 = new Heladera("Heladera el Ateneo",ubicacion3, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());
        Heladera heladera4 = new Heladera("Heladera Puerto Madero",ubicacion4, LocalDate.now(),colaborador0,modeloHeladera,true,new ArrayList<>());


        Repositorio repositorioHeladera = ServiceLocator.getRepository(Repositorio.class);
        repositorioHeladera.guardar(heladera);
        repositorioHeladera.guardar(heladera1);
        repositorioHeladera.guardar(heladera2);
        repositorioHeladera.guardar(heladera3);
        repositorioHeladera.guardar(heladera4);


    }
}
