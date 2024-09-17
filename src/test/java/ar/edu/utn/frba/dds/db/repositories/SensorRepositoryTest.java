package ar.edu.utn.frba.dds.db.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SensorRepositoryTest  {
  SensorRepository sensorRepository = (SensorRepository) ServiceLocator.getRepository(TipoRepo.SENSOR);
  Repositorio repositorio = ServiceLocator.getRepository(TipoRepo.HELADERA);

  @Test @DisplayName("Se encuentra un sensor por la heladera en la que esta")
  public void buscarSensorPorHeladera() {
    Heladera heladera = new Heladera();
    Sensor sensor = new Sensor(heladera,null);
    repositorio.guardar(heladera);
    sensorRepository.guardar(sensor);

    List<Sensor> sensorBuscado = sensorRepository.buscarPorHeladera(heladera.getId());
    assertEquals(sensor.getId(), sensorBuscado.get(0).getId());

  }
}
