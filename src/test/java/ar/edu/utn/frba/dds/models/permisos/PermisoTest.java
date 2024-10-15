package ar.edu.utn.frba.dds.models.permisos;

import ar.edu.utn.frba.dds.simeal.models.usuario.Permiso;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoMetodoHttp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PermisoTest {
    @Test
    public void TestearURLEspecifica(){
        Permiso p = new Permiso("/user/name", TipoMetodoHttp.GET);
        Assertions.assertTrue(p.isAllowed("/user/name", TipoMetodoHttp.GET));
        Assertions.assertTrue(p.isAllowed("user/name", TipoMetodoHttp.GET));
        Assertions.assertTrue(p.isAllowed("user/name/", TipoMetodoHttp.GET));
        Assertions.assertTrue(p.isAllowed("/user/name/", TipoMetodoHttp.GET));
        Assertions.assertFalse(p.isAllowed("username", TipoMetodoHttp.GET));
    }

    @Test
    public void TestearMetodo(){
        Permiso p = new Permiso("/home", TipoMetodoHttp.DELETE);
        Assertions.assertTrue(p.isAllowed("/home", TipoMetodoHttp.DELETE));
        Assertions.assertFalse(p.isAllowed("/home", TipoMetodoHttp.POST));
        Assertions.assertFalse(p.isAllowed("/home", TipoMetodoHttp.GET));
    }

    @Test
    public void TestearRegex1(){
        Permiso p = new Permiso("/home/.*", TipoMetodoHttp.DELETE);
        Assertions.assertTrue(p.isAllowed("/home/user", TipoMetodoHttp.DELETE));
        Assertions.assertFalse(p.isAllowed("/lal/user", TipoMetodoHttp.DELETE));
        Assertions.assertTrue(p.isAllowed("/home/user/view", TipoMetodoHttp.DELETE));
    }

    @Test
    public void TestearRegex2(){
        Permiso p = new Permiso("/home/user/(delete|view)",
                TipoMetodoHttp.DELETE);
        Assertions.assertTrue(p.isAllowed("/home/user/delete", TipoMetodoHttp.DELETE));
        Assertions.assertTrue(p.isAllowed("/home/user/view", TipoMetodoHttp.DELETE));
        Assertions.assertFalse(p.isAllowed("/home/user/lal", TipoMetodoHttp.DELETE));
        Assertions.assertFalse(p.isAllowed("/home/user/view/lal", TipoMetodoHttp.DELETE));
    }

    @Test
    public void TestearRegex3() {
        Permiso p = new Permiso("/formulario/\\d+", TipoMetodoHttp.GET);
        Assertions.assertTrue(p.isAllowed("/formulario/123", TipoMetodoHttp.GET));
        Assertions.assertFalse(p.isAllowed("/formulario/123/elicapo", TipoMetodoHttp.GET));
    }

}
