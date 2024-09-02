package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositoryGenerico<T extends Persistente> implements Repository<T>, WithSimplePersistenceUnit {
    @Override
    public void guardar(T o) {
        entityManager().persist(o);
    }

    @Override
    public void eliminar(Long id) {
        T entity = (T) buscarPorId(id);
        if (entity != null) {
            entityManager().remove(entity);
        }
    }

    @Override
    public void desactivar(Long id) {
        T entity = (T) buscarPorId(id);
        if (entity != null) {
            entity.setActivo(false);
            entityManager().merge(entity);
        }
    }

    @Override
    public void actualizar(T o) {
        withTransaction(() -> {
            entityManager().merge(o);//UPDATE
        });
    }

    @Override
    public List<T> obtenerTodos() {
        return List.of();
    }

    public T buscarPorId(Long id) {
        return entityManager().find(getEntityClass(), id);
    }

    private Class<T> getEntityClass() {
        return (Class<T>) EstadoHeladera.class;
    }

}
