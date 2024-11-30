package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

public class SensorRepository extends Repositorio {
  public Sensor buscarPorHeladera(Long id) {
    Sensor sensor;
    beginTransaction();
    sensor = (Sensor) entityManager()
      .createQuery("FROM " + Sensor.class.getName() + " WHERE heladera_id = :id")
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .setParameter("id", id)
      .getSingleResult();  // Se asegura de obtener solo un resultado
    commitTransaction();

    if (sensor != null) {
      entityManager().refresh(sensor);
    }
    return sensor;
  }}
