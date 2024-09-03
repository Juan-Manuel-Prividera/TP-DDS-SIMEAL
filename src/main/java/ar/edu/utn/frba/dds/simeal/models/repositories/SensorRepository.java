package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorRepository extends Repositorio {
  public List<Sensor> buscarPorHeladera(Long id) {
    List<Sensor> modelos = new ArrayList<>();
    beginTransaction();
    modelos = entityManager()
      .createQuery("from " + Sensor.class.getName() + " where heladera_id =:id")
      .setParameter("heladera_id", id)
      .getResultList();
    commitTransaction();
    return modelos;
  }}
