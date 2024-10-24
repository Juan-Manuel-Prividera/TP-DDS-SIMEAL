package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;

import javax.persistence.EntityGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuscripcionesRepository extends Repositorio {

 public List<Suscripcion> buscarPor(Heladera heladera) {
   List<Suscripcion> suscripciones = new ArrayList<>();
   beginTransaction();
   suscripciones = entityManager()
     .createQuery("FROM " + Suscripcion.class.getName() + " WHERE heladera_id = :id")
     .setParameter("id", heladera.getId())
     .getResultList();
   commitTransaction();
   return suscripciones;
  }

  public List<Suscripcion> buscarPor(Colaborador colaborador) {
    List<Suscripcion> suscripciones = new ArrayList<>();
    beginTransaction();
    suscripciones = entityManager()
      .createQuery("FROM " + Suscripcion.class.getName() + " WHERE suscriptor_id = :id")
      .setParameter("id", colaborador.getId())
      .getResultList();
    commitTransaction();
    return suscripciones;
  }

  public void refresh(Suscripcion suscripcion) {
    entityManager().refresh(suscripcion);
  }
}
