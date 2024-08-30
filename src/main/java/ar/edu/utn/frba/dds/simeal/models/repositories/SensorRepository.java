package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import java.util.Optional;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SensorRepository implements Repository<Sensor> {
  private List<Sensor> sensores;


  public SensorRepository() {
    sensores = new ArrayList<>();
  }
  public void guardar(Sensor sensor) {
    sensores.add(sensor);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Sensor sensor) {

  }

  @Override
  public List<Sensor> obtenerTodos() {
    return List.of();
  }

  public List<Sensor> getAll() {
    return sensores;
  }

  public void eliminar(Sensor sensor) {
    sensores.remove(sensor);
  }

  public Sensor buscarSegun(String nombreHeladera) {
    return this.sensores
        .stream()
        .filter(s -> s.getHeladera().getNombre().equals(nombreHeladera))
        .findFirst()
        .orElse(null);
  }


}
