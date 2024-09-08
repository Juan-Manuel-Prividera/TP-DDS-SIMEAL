package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorRepository extends Repositorio {
  public List<Sensor> buscarPorHeladera(Long id) {
    List<Sensor> sensores = new ArrayList<>();
    beginTransaction();
    sensores = entityManager()
      .createQuery("FROM " + Sensor.class.getName() + " WHERE heladera_id =:id")
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return sensores;
  }}
