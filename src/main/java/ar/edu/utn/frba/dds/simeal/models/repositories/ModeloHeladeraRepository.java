package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;

import java.util.ArrayList;
import java.util.List;

public class ModeloHeladeraRepository extends Repositorio{
    public List<ModeloHeladera> buscarPorNombre(String nombre) {
        List<ModeloHeladera> modelos = new ArrayList<>();
        beginTransaction();
        modelos = entityManager()
                .createQuery("from " + ModeloHeladera.class.getName() + " where nombre =:name")
                .setParameter("name", nombre)
                .getResultList();
        commitTransaction();
        return modelos;
    }
}

