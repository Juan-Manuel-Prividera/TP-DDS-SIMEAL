package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

import java.util.List;

public class ColaboradorRespository {
    private static ColaboradorRespository instance;

    private ColaboradorRespository() {

    }

    public static ColaboradorRespository getInstance() {
        if (instance == null) {
            instance = new ColaboradorRespository();
        }
        return instance;
    }

    public List<Colaborador> getAllColaboradores() {
        return null;
        // TODO
    }
}
