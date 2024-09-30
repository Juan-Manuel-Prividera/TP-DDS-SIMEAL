package ar.edu.utn.frba.dds.simeal.service.cronjobs;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

import java.util.List;

// CRONTAB
// 0 0 * * * java -jar "/path/to/ResetearUsos.jar"
public class ResetearUsos {
    public static void main(String[] args) {
        List<TarjetaPersonaVulnerable> tarjetas = (List<TarjetaPersonaVulnerable>) ServiceLocator.
          getRepository(Repositorio.class).obtenerTodos(TarjetaPersonaVulnerable.class);

        for (TarjetaPersonaVulnerable tarjeta : tarjetas) {
            tarjeta.resetearUsos();
        }

    }
}
