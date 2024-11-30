package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository extends Repositorio {

 public List<Suscripcion> buscarPor(Heladera heladera) {
   List<Suscripcion> suscripciones = new ArrayList<>();
   beginTransaction();
   suscripciones = entityManager()
     .createQuery("FROM " + Suscripcion.class.getName() + " WHERE heladera_id = :id AND activo = true")
     .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
     .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
     .setParameter("id", heladera.getId())
     .getResultList();
   commitTransaction();
   if (suscripciones != null) {
     for (Suscripcion s : suscripciones) {
       entityManager().refresh(s);
     }
   }
   return suscripciones;
  }

  public List<Suscripcion> buscarPor(Colaborador colaborador) {
    List<Suscripcion> suscripciones = new ArrayList<>();
    beginTransaction();
    suscripciones = entityManager()
      .createQuery("FROM " + Suscripcion.class.getName() + " WHERE suscriptor_id = :id AND activo = true")
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .setParameter("id", colaborador.getId())
      .getResultList();
    commitTransaction();
    if (suscripciones != null) {
      for (Suscripcion s : suscripciones) {
        entityManager().refresh(s);
      }
    }
    return suscripciones;
  }

}
