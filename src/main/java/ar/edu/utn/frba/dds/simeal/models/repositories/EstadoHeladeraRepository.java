package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class EstadoHeladeraRepository implements Repository<EstadoHeladera>, WithSimplePersistenceUnit {
    public void guardar(EstadoHeladera estado) {
        entityManager().persist(estado);
    }

    @Override
    public void eliminar(Long id) {
        entityManager().remove(buscarPorId(id));//DELETE
    }

    @Override
    public void actualizar(EstadoHeladera estadoHeladera) {
        withTransaction(() -> {
            entityManager().merge(estadoHeladera);//UPDATE
        });
    }

    @Override
    public List<EstadoHeladera> obtenerTodos() {
        return List.of();
    }

    public void desactivar(Long id) {
        EstadoHeladera estado = buscarPorId(id);
        estado.setActivo(false);
        entityManager().merge(estado);
    }

    public EstadoHeladera buscarPorId(Long id) {
        return entityManager().find(EstadoHeladera.class, id);
    }


}
