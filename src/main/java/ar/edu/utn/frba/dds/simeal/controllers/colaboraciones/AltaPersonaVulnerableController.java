package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

import java.time.LocalDate;

public class AltaPersonaVulnerableController {

  public void create(TarjetaPersonaVulnerable tarjetaPersonaVulnerable, PersonaVulnerable personaVulnerable) {
    // TODO: Cambiar 1L por el de la sesion
    Colaborador colaborador = (Colaborador) ServiceLocator.getRepository(Repositorio.class).buscarPorId(1L, Colaborador.class);
    DarDeAltaPersonaVulnerable darDeAltaPersonaVulnerable = new DarDeAltaPersonaVulnerable(colaborador,personaVulnerable,tarjetaPersonaVulnerable);

    ServiceLocator.getRepository(ColaboracionRepository.class).guardar(darDeAltaPersonaVulnerable);
  }
}
