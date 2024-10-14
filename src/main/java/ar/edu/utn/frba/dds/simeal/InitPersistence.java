package ar.edu.utn.frba.dds.simeal;

import ar.edu.utn.frba.dds.simeal.models.usuario.Permiso;
import ar.edu.utn.frba.dds.simeal.models.usuario.Rol;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoMetodoHttp;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import org.apache.commons.io.input.buffer.PeekableInputStream;

import java.util.regex.Pattern;

// Este archivo mete en la BD algunos datos hardcodeados, como los tipos de roles, los formularios, etc...
public class InitPersistence {
    public static void main(String[] args){

        initPermisosRolesYUsuarios();

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
        Permiso postFormularios = new Permiso("formularios", TipoMetodoHttp.POST);
        Permiso getFormulario = new Permiso(Pattern.compile("formularios/\\d*"), TipoMetodoHttp.GET);
        Permiso postPregunta = new Permiso(Pattern.compile("formularios/\\d*/pregunta"), TipoMetodoHttp.POST);

        Permiso deletePregunta = new Permiso(Pattern.compile("formularios/\\d*/pregunta/\\d*"), TipoMetodoHttp.DELETE);
        Permiso deleteFormulario = new Permiso(Pattern.compile("formularios/\\d*"), TipoMetodoHttp.DELETE);

        // Humano
        Permiso getTarjetas = new Permiso(Pattern.compile("tarjeta/.*"),TipoMetodoHttp.GET);
        Permiso postTarjetas = new Permiso(Pattern.compile("tarjeta/.*"),TipoMetodoHttp.POST);

        Permiso getHeladera = new Permiso(Pattern.compile("heladera/.*"), TipoMetodoHttp.GET);


    }
}
