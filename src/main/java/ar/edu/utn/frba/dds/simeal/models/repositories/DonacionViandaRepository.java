package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;

import java.util.List;

public class DonacionViandaRepository {
    private static DonacionViandaRepository instance;

    private DonacionViandaRepository() {

    }

    public static DonacionViandaRepository getInstance() {
        if (instance == null) {
            instance = new DonacionViandaRepository();
        }
        return instance;
    }

    public List<DonarVianda> getAllDonaciones() {
        return null;
    }
}
