package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;

import java.util.ArrayList;
import java.util.List;

public class TarjetaPersonaVulnerableRepository {
    private static List<TarjetaPersonaVulnerable> tarjetas;
    static private TarjetaPersonaVulnerableRepository instance;

    private TarjetaPersonaVulnerableRepository() {
        tarjetas = new ArrayList<TarjetaPersonaVulnerable>();
    }


    public static List<TarjetaPersonaVulnerable> getAll() {
        return tarjetas;
    }

}
