package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Persistente p) {
      try{
        beginTransaction();
        entityManager().persist(p);
        entityManager().flush();
        entityManager().refresh(p);
        entityManager().clear();
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
       entityManager().clear();
       commitTransaction();
    }

    public void actualizar(Persistente p) {
        beginTransaction();
        entityManager().merge(p);
        commitTransaction();
        // Si ponemos lo de flush refresh y clear aca se rompe
    }

    public List<? extends Persistente> obtenerTodos(Class<? extends Persistente> clase) {
      try {
        beginTransaction();
        List<Persistente> persistentes = (List<Persistente>) entityManager()
          .createQuery("FROM " + clase.getName() + " WHERE activo = true")
          .getResultList();
        commitTransaction();
        return persistentes;
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }

    public Persistente buscarPorId(Long id, Class<? extends Persistente> clase) {
      try {
        entityManager().clear();
        beginTransaction();
        Persistente persistente = entityManager().find(clase, id);
        commitTransaction();
        if (persistente.getActivo())
          return persistente;
        return null;
      } catch (Exception e) {
        rollbackTransaction();
        throw e;
      }
    }

    public void refresh(Persistente p) {
      entityManager().refresh(p);
    }

}