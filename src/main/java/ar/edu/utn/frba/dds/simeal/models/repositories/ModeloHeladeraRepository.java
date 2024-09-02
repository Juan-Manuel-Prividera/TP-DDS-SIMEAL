package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class ModeloHeladeraRepository implements Repository<ModeloHeladera>, WithSimplePersistenceUnit {
    public void guardar(ModeloHeladera modelo) {
        entityManager().persist(modelo);
    }

    @Override
    public void eliminar(Long id) {
        entityManager().remove(buscarPorId(id));//DELETE
    }

    @Override
    public void actualizar(ModeloHeladera modeloHeladera) {
        withTransaction(() -> {
            entityManager().merge(modeloHeladera);//UPDATE
        });
    }

    @Override
    public List<ModeloHeladera> obtenerTodos() {
        return List.of();
    }

    public void desactivar(Long id) {
        ModeloHeladera modelo = buscarPorId(id);
        modelo.setActivo(false);
        entityManager().merge(modelo);
    }

    public ModeloHeladera buscarPorId(Long id) {
        return entityManager().find(ModeloHeladera.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<ModeloHeladera> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("from " + ModeloHeladera.class.getName() + " where nombre =:name")
                .setParameter("name", nombre)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<ModeloHeladera> buscarTodos() {
        return entityManager()
                .createQuery("from " + ModeloHeladera.class.getName())
                .getResultList();
    }
}

