package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.ArrayList;
import java.util.List;

public class EncargoTecnicoRepostiry extends Repositorio {
  public List<EncargoTecnico> getPorTecnico(Long id) {
    List<EncargoTecnico> encargos = new ArrayList<>();
    beginTransaction();
    encargos = entityManager()
      .createQuery(" FROM " + EncargoTecnico.class.getName() + " WHERE tecnico_id = :id", EncargoTecnico.class)
      .setParameter("id", id)
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .getResultList();
    commitTransaction();
    if (encargos != null) {
      for (EncargoTecnico e : encargos) {
        entityManager().refresh(e);
      }
    }
    return encargos;
  }
}
