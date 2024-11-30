package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import java.util.List;

public class Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Persistente p) {
      try{
        beginTransaction();
        entityManager().persist(p);
        entityManager().flush();
        commitTransaction();
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }

    public void eliminar(Long id, Class< ? extends Persistente> clase) {
        Persistente entity = buscarPorId(id, clase);
        if (entity != null) {
            beginTransaction();
            entityManager().remove(entity);
            commitTransaction();
        }
    }

    public void desactivar(Persistente p) {
       beginTransaction();
       p.setActivo(false);
       entityManager().merge(p);

       entityManager().flush();
       entityManager().refresh(p);

       commitTransaction();
    }

    public void actualizar(Persistente p) {
        beginTransaction();

        Persistente entidadGestionada = entityManager().merge(p);

        entityManager().flush();
        entityManager().refresh(entidadGestionada);

        commitTransaction();
        // Si ponemos lo de flush refresh y clear aca se rompe
    }

    public List<? extends Persistente> obtenerTodos(Class<? extends Persistente> clase) {
      try {
        beginTransaction();
        List<Persistente> persistentes = (List<Persistente>) entityManager()
          .createQuery("FROM " + clase.getName() + " WHERE activo = true")
          .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
          .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
          .getResultList();
        commitTransaction();
        if (persistentes != null) {
          for (Persistente persistente : persistentes) {
            entityManager().refresh(persistente);
          }
        }
        return persistentes;
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }

    public Persistente buscarPorId(Long id, Class<? extends Persistente> clase) {
      try {
        beginTransaction();
        Persistente persistente = (Persistente) entityManager()
          .createQuery("FROM " + clase.getName() + " WHERE activo = true AND id = " + id)
          .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
          .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
          .getSingleResult();
        commitTransaction();
        if (persistente != null) {
          entityManager().refresh(persistente);
        }
        return persistente;
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }

    public void refresh(Persistente p) {
      entityManager().refresh(p);
    }

}