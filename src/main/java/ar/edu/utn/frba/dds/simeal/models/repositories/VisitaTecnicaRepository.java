package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.ArrayList;
import java.util.List;

public class VisitaTecnicaRepository extends Repositorio {
  public List<VisitaTecnica> getPorTecnico(Long id) {
    List<VisitaTecnica> visitas = new ArrayList<>();
    try {
      beginTransaction();
      visitas = entityManager()
        .createQuery(" FROM " + VisitaTecnica.class.getName() + " WHERE tecnico_id = :id", VisitaTecnica.class)
        .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
        .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
        .setParameter("id", id)
        .getResultList();
      commitTransaction();
    } catch (Exception e) {
      Logger.error("Error al obtener resultado en VisitaTecnicaRepository  -  ", e.getMessage());
    }
    return visitas;
  }

  public List<VisitaTecnica> getPorHeladera(Long id) {
    List<VisitaTecnica> visitas = new ArrayList<>();
    try {
      beginTransaction();
      visitas = entityManager()
        .createQuery(" FROM " + VisitaTecnica.class.getName() + " WHERE heladera_id = :id", VisitaTecnica.class)
        .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
        .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
        .setParameter("id", id)
        .getResultList();
      commitTransaction();

      if (visitas != null) {
        for (VisitaTecnica v: visitas) {
          entityManager().refresh(v);
        }
      }
    } catch (Exception e) {
      Logger.error("Error al obtener resultado en VisitaTecnicaRepository  -  ", e.getMessage());
    }
    return visitas;
  }
}
