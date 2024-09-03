package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.List;

public class Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Persistente p) {
        beginTransaction();
        entityManager().persist(p);
        commitTransaction();
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
        commitTransaction();
    }

    public void actualizar(Persistente p) {
        beginTransaction();
        entityManager().merge(p);
        commitTransaction();
    }

    public List<? extends Persistente> obtenerTodos(Class<? extends Persistente> clase) {
      beginTransaction();
      List<Persistente> persistentes = new ArrayList<>(entityManager()
        .createQuery("FROM " + clase.getName(), clase)
        .getResultList());
        commitTransaction();

        return persistentes;
    }

    public Persistente buscarPorId(Long id, Class<? extends Persistente> clase) {
        beginTransaction();
        Persistente persistente = entityManager().find(clase, id);
        commitTransaction();
        return persistente;
    }
}