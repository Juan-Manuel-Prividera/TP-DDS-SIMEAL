package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;

public class SensorRepository extends Repositorio {
  public Sensor buscarPorHeladera(Long id) {
    Sensor sensores;
    beginTransaction();
    sensores = (Sensor) entityManager()
      .createQuery("FROM " + Sensor.class.getName() + " WHERE heladera_id =:id")
      .setParameter("id", id);
    commitTransaction();
    return sensores;
  }}
