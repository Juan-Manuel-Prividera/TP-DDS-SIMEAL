package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

public class TarjetaPersonaVulnerableRepository extends Repositorio{
  public TarjetaPersonaVulnerable getPorNumero(String codigo) {
    TarjetaPersonaVulnerable personaVulnerable;
    beginTransaction();
    personaVulnerable = entityManager()
      .createQuery(" FROM " + TarjetaPersonaVulnerable.class.getName() + " WHERE codigo= :codigo", TarjetaPersonaVulnerable.class)
      .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
      .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
      .setParameter("codigo", codigo).getResultList().get(0);
    commitTransaction();

    if (personaVulnerable != null) {
        entityManager().refresh(personaVulnerable);
    }
    return personaVulnerable;
  }
}

