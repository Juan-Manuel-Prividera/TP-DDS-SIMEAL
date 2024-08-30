package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;

import java.util.ArrayList;
import java.util.List;

public class TarjetaPersonaVulnerableRepository implements Repository<TarjetaPersonaVulnerable> {
    private static List<TarjetaPersonaVulnerable> tarjetas;

    public TarjetaPersonaVulnerableRepository() {
        tarjetas = new ArrayList<TarjetaPersonaVulnerable>();
    }


    public static List<TarjetaPersonaVulnerable> getAll() {
        return tarjetas;
    }


    @Override
    public void guardar(TarjetaPersonaVulnerable tarjetaPersonaVulnerable) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public void actualizar(TarjetaPersonaVulnerable tarjetaPersonaVulnerable) {

    }

    @Override
    public List<TarjetaPersonaVulnerable> obtenerTodos() {
        return List.of();
    }
}
