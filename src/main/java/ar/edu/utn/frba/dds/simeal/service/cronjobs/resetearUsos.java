package ar.edu.utn.frba.dds.simeal.service.cronjobs;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaPersonaVulnerableRepository;

import java.util.List;

// CRONTAB
// 0 0 * * * java -jar "/path/to/resetearUsos.jar"
public class resetearUsos {
    public static void main(String[] args) {
        List<TarjetaPersonaVulnerable> tarjetas = TarjetaPersonaVulnerableRepository.getAll();

        for (TarjetaPersonaVulnerable tarjeta : tarjetas) {
            tarjeta.resetearUsos();
        }

    }
}
