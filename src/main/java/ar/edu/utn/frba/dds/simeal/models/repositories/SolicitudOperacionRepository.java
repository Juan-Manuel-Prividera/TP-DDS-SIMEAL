package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.ArrayList;
import java.util.List;

public class SolicitudOperacionRepository extends Repositorio {
  public List<SolicitudOperacionHeladera> getPorTarjetaColaborador(Long id) {
    List<SolicitudOperacionHeladera> solicitudOperacionHeladera = new ArrayList<>();
    beginTransaction();
    solicitudOperacionHeladera = entityManager()
      .createQuery(" FROM " + SolicitudOperacionHeladera.class.getName() + " WHERE tarjeta_colaborador_id= :id AND hora_solicitud >= CURRENT_DATE ", SolicitudOperacionHeladera.class)
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .setParameter("id", id).getResultList();
    commitTransaction();
    if (solicitudOperacionHeladera != null) {
      for (SolicitudOperacionHeladera s : solicitudOperacionHeladera) {
        entityManager().refresh(s);
      }
    }
    return solicitudOperacionHeladera;
  }
}
