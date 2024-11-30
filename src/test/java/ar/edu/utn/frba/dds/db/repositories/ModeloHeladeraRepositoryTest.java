package ar.edu.utn.frba.dds.db.repositories;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.repositories.ModeloHeladeraRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModeloHeladeraRepositoryTest implements SimplePersistenceTest {
  ModeloHeladeraRepository modeloHeladeraRepository = (ModeloHeladeraRepository) ServiceLocator.getRepository(ModeloHeladeraRepository.class);
//  @Test
//  public void buscarModeloPorNombreTest() {
//    ModeloHeladera modeloHeladera = new ModeloHeladera("Modelo de Prueba",1,2,3);
//    ModeloHeladera modeloHeladera1 = new ModeloHeladera("Modelo",1,2,3);
//    modeloHeladeraRepository.guardar(modeloHeladera);
//    modeloHeladeraRepository.guardar(modeloHeladera1);
//
//   List<ModeloHeladera> modeloBuscado = modeloHeladeraRepository.buscarPorNombre("Modelo de Prueba");
//    assertEquals(modeloHeladera.getId(), modeloBuscado.get(0).getId());
//  }

}
