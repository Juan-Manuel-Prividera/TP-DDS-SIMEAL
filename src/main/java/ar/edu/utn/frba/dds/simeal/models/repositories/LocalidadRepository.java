package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Localidad;

import java.util.ArrayList;
import java.util.List;

public class LocalidadRepository extends Repositorio{
  public Localidad buscarPorNombre(String nombre) {
    Localidad localidad;
    beginTransaction();
    localidad = (Localidad) entityManager()
      .createQuery("from " + Localidad.class.getName() + " where nombre =:name")
      .setParameter("name", nombre)
      .getResultList().get(0);
    commitTransaction();
    return localidad;
  }
}
