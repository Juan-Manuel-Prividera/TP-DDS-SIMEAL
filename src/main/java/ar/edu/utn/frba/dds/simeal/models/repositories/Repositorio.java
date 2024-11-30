package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
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
      Logger.info("Se guardo la entidad: %s - Id: %s",p.getClass(), p.getId());
    }

    public void eliminar(Long id, Class< ? extends Persistente> clase) {
        Persistente entity = buscarPorId(id, clase);
        if (entity != null) {
            beginTransaction();
            entityManager().remove(entity);
            commitTransaction();
        Logger.info("Se eliminó la entidad: %s - Id: %s",entity.getClass(), entity.getId());
        }
      Logger.warn("Se intentó eliminar la entidad que no existe en la bd");
    }

    public void desactivar(Persistente p) {
       beginTransaction();
       p.setActivo(false);
       entityManager().merge(p);

       entityManager().flush();
       entityManager().refresh(p);

       commitTransaction();

       Logger.info("Se eliminó la entidad: %s - Id: %s",p.getClass(), p.getId());
    }

    public void actualizar(Persistente p) {
        beginTransaction();

        Persistente entidadGestionada = entityManager().merge(p);

        entityManager().flush();
        entityManager().refresh(entidadGestionada);

        commitTransaction();
      Logger.info("Se actualizó la entidad: %s - Id: %s",p.getClass(), p.getId());
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
          Logger.info("Entidades %s obtenidas: %s", persistentes.getClass(), persistentes.toString());
        }else
          Logger.warn("No se econtró ninguna entidad de tipo: %s",clase.getName());
        return persistentes;
      } catch (Exception e) {
        rollbackTransaction();
        Logger.error("Ocurrió un error al obtener todos los %s - %s", clase.getName(), e);
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
          Logger.info("Entidad '%s' obtenida: %s", clase.getName(), persistente.toString());
        } else
          Logger.warn("No se encontró ninguna entidad de tipo: '%s' - Id: %s", clase.getName(), id);

        return persistente;
      } catch (Exception e) {
        rollbackTransaction();
        Logger.error("Ocurrió un error al obtener '%s' por Id '%s' - %s", clase.getName(), id, e.toString());
        throw e;
      }
    }

    public void refresh(Persistente p) {
      entityManager().refresh(p);
      Logger.info("Se refrescó la entidad '%s' con Id '%s'", p.getClass(), p.getId());
    }
}