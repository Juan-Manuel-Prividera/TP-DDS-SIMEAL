package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;

public class SensorRepository extends Repositorio {
  public Sensor buscarPorHeladera(Long id) {
    Sensor sensor;
    beginTransaction();
    sensor = (Sensor) entityManager()
      .createQuery("FROM " + Sensor.class.getName() + " WHERE heladera_id = :id")
      .setParameter("id", id)
      .getSingleResult();  // Se asegura de obtener solo un resultado
    commitTransaction();
    return sensor;
  }}
