package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import java.util.Optional;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SensorRepository {
  private List<Sensor> sensores;

  static private SensorRepository instance;

  public static SensorRepository getInstance() {
    if(instance == null)
      return new SensorRepository();
    return instance;
  }

  private SensorRepository() {
    sensores = new ArrayList<>();
  }
  public void guardar(Sensor sensor) {
    sensores.add(sensor);
  }

  public List<Sensor> getAll() {
    return sensores;
  }

  public void eliminar(Sensor sensor) {
    sensores.remove(sensor);
  }

  public Sensor buscarSegun(String nombreHeladera){
    return this.sensores
        .stream()
        .filter(s -> s.getHeladera().getNombre().equals(nombreHeladera))
        .findFirst()
        .orElse(null);
  }

}
