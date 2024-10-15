package ar.edu.utn.frba.dds.simeal;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.TipoPregunta;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.*;
import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;

import java.util.List;
import java.util.regex.Pattern;

// Este archivo mete en la BD algunos datos hardcodeados, como los tipos de roles, los formularios, etc...
public class InitPersistence {
    public static void main(String[] args){
        initPermisosRolesYUsuarios();
        initFormularios();
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

        Permiso getColaboraciones = new Permiso("colaboraciones", TipoMetodoHttp.GET);
        Permiso postDonarDinero = new Permiso("colaboraciones/donarDinero", TipoMetodoHttp.POST);

        Permiso getHeladera = new Permiso("heladera", TipoMetodoHttp.GET);
        Permiso getHeladeraEspecifico = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.GET);
        Permiso postHeladera = new Permiso("/heladera/(?!suscribirse/).*",
                TipoMetodoHttp.POST);

        Permiso getSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);
        Permiso postSuscribirHeladera = new Permiso("/heladera/suscribirse/\\d+",
                TipoMetodoHttp.GET);

        Permiso getHeladeras = new Permiso("heladeras", TipoMetodoHttp.GET);
        Permiso getOfertas = new Permiso("ofertas", TipoMetodoHttp.GET);
        Permiso getOferta = new Permiso("oferta/\\d+", TipoMetodoHttp.GET);

        // Crear roles
        List<Permiso> permisosHumano = List.of(
                getHome, getTarjeta, getTarjetas, postTarjetas, postDonarDinero, getColaboraciones,
                getHeladera, getHeladeraEspecifico, postHeladera, getSuscribirHeladera, postSuscribirHeladera,
                getHeladeras, getOfertas, getOferta
        );
        Rol humano = new Rol(TipoRol.HUMANO, permisosHumano);

        List<Permiso> permisosJuridico = List.of(
                getHome, getHeladera, postHeladera, postDonarDinero, getColaboraciones,
                getHeladeras, getOfertas, getOferta
        );
        Rol juridico = new Rol(TipoRol.JURIDICO, permisosJuridico);

        List<Permiso> permisosAdmin = List.of(
                getMigracion, postMigracionUpload, getReportes, getCambiarModo,
                getFormularios, getFormulario, postFormularios, postPregunta,
                deletePregunta, deleteFormulario, getTarjeta, getTarjetas, postTarjetas, getColaboraciones,
                postDonarDinero,
                getHeladeraEspecifico, getHeladera, getHeladeras, postHeladera, getSuscribirHeladera,
                getOferta, getOfertas, postSuscribirHeladera
        );
        Rol admin = new Rol(TipoRol.ADMIN, permisosAdmin);

        Repositorio repo = new Repositorio();
        repo.guardar(humano);
        repo.guardar(juridico);
        repo.guardar(admin);

        Usuario usuarioAdmin = new Usuario("admin", PasswordHasher.hashPassword("admin"), List.of(admin));
        repo.guardar(usuarioAdmin);

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

}
