package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository extends Repositorio {

 public List<Suscripcion> buscarPor(Heladera heladera) {
   List<Suscripcion> suscripciones = new ArrayList<>();
   beginTransaction();
   suscripciones = entityManager().createQuery("FROM " + Suscripcion.class.getName() + " WHERE heladera_id = :id")
     .setParameter("id", heladera.getId())
     .getResultList();
   commitTransaction();
   return suscripciones;
  }
}
