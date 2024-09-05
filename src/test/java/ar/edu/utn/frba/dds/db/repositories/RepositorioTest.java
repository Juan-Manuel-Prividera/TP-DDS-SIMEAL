package ar.edu.utn.frba.dds.db.repositories;


import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.repositories.ModeloHeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class RepositorioTest implements SimplePersistenceTest {
  ModeloHeladeraRepository repositorio = (ModeloHeladeraRepository) ServiceLocator.getRepository(TipoRepo.MODELO_HELADERA);

  @Test
  public void seGuardaYLeeCorrectamente() {
    ModeloHeladera modeloHeladera = new ModeloHeladera();
    Assertions.assertNull(modeloHeladera.getId());
    repositorio.guardar(modeloHeladera);
    Assertions.assertNotNull(modeloHeladera.getId());
  }

  @Test
  public void seDesactivaCorrectamente() {
    ModeloHeladera modeloHeladera = new ModeloHeladera();
    Assertions.assertTrue(modeloHeladera.getActivo());
    repositorio.desactivar(modeloHeladera);
    Assertions.assertFalse(modeloHeladera.getActivo());
  }
}
