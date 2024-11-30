package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OfertaRepository extends Repositorio{
  public List<Oferta> getPorColaborador(Long Id) {
    List<Oferta> colaboraciones = new ArrayList<>();
    beginTransaction();
    colaboraciones = entityManager()
      //Aparece como error de sintaxis, pero funciona. Momento xd
      .createQuery("FROM " + Oferta.class.getName() + " WHERE colaborador_id= :id", Oferta.class)
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .setParameter("id", Id)
      .getResultList();
    commitTransaction();
    if (colaboraciones != null) {
      for (Oferta o : colaboraciones) {
        entityManager().refresh(o);
      }
    }
    return colaboraciones;
  }
}


