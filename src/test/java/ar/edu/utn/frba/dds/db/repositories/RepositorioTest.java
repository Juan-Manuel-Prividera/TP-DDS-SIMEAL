package ar.edu.utn.frba.dds.db.repositories;


import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RepositorioTest implements SimplePersistenceTest {
  Repositorio repositorio;

  @BeforeEach
  public void init() {
    repositorio = ServiceLocator.getRepository(Repositorio.class);
  }


  @Test @DisplayName("Se persiste correctamente con el metodo guardar")
  public void guardarTest() {
    Colaborador colaborador = new Colaborador(new Documento(TipoDocumento.DNI,"12345667"), "Tomas", "Pauza");
    repositorio.guardar(colaborador);

    assertNotNull(colaborador.getId());
    repositorio.eliminar(colaborador.getId(), Colaborador.class);
  }

//  @Test @DisplayName("Se obtienen correctamente todos los objetos con obtenerTodos()")
//  public void obtenerTodosTest() {
//    Colaborador colaborador1 = new Colaborador();
//    Colaborador colaborador2 = new Colaborador();
//
//    repositorio.guardar(colaborador1);
//    repositorio.guardar(colaborador2);
//
//    List<Colaborador> colaboradores = (List<Colaborador>) repositorio.obtenerTodos(Colaborador.class);
//    System.out.println(colaboradores.get(0).getNombre());
//    assertEquals(2, colaboradores.size());
//    assertEquals(colaboradores.get(0).getId(), colaborador1.getId());
//    assertEquals(colaboradores.get(1).getId(), colaborador2.getId());
//
//    repositorio.eliminar(colaborador1.getId(), Colaborador.class);
//    repositorio.eliminar(colaborador2.getId(), Colaborador.class);
//  }
//

  @Test @DisplayName("Se desactiva correctamente a los Persistentes")
  public void desactivarTest() {
    Colaborador colaborador = new Colaborador();
    assertTrue(colaborador.getActivo());
    repositorio.guardar(colaborador);
    repositorio.desactivar(colaborador);
    assertFalse(colaborador.getActivo());

//    repositorio.eliminar(colaborador.getId(), Colaborador.class);
  }

//  @Test @DisplayName("Se actualizan correctamente los persistentes")
//  public void actualizarTest() {
//    Colaborador colaborador = new Colaborador();
//    colaborador.setNombre("Pablo");
//    repositorio.guardar(colaborador);
//
//    colaborador.setNombre("Roman");
//    repositorio.actualizar(colaborador);
//
//    List<Colaborador> colaboradores = (List<Colaborador>) repositorio.obtenerTodos(Colaborador.class);
//    assertEquals("Roman",colaboradores.get(0).getNombre());
//
//    repositorio.eliminar(colaborador.getId(), Colaborador.class);
//  }

  @Test @DisplayName("Se encuentra Persistente por Id correctamente")
  public void buscarPorIdTest() {
    Colaborador colaborador = new Colaborador();
    repositorio.guardar(colaborador);

    Colaborador colab = (Colaborador) repositorio.buscarPorId(colaborador.getId(), Colaborador.class);
    assertEquals(colab.getId(), colaborador.getId());

    repositorio.eliminar(colaborador.getId(), Colaborador.class);
  }

}
