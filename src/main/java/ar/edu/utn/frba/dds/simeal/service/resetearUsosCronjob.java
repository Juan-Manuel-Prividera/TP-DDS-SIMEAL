package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaPersonaVulnerableRepository;

import java.util.List;

public class resetearUsosCronjob {
    public static void main(String[] args) {
        List<TarjetaPersonaVulnerable> tarjetas = TarjetaPersonaVulnerableRepository.getAll();

        for (TarjetaPersonaVulnerable tarjeta : tarjetas) {
            tarjeta.resetearUsos();
        }

    }
}
