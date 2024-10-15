package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

public class AltaPersonaVulnerableController {

  public void create(TarjetaPersonaVulnerable tarjetaPersonaVulnerable, PersonaVulnerable personaVulnerable, Context ctx) {
    Colaborador colaborador = (Colaborador) ServiceLocator
      .getRepository(Repositorio.class)
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    DarDeAltaPersonaVulnerable darDeAltaPersonaVulnerable = new DarDeAltaPersonaVulnerable(colaborador,personaVulnerable,tarjetaPersonaVulnerable);

    ServiceLocator.getRepository(ColaboracionRepository.class).guardar(darDeAltaPersonaVulnerable);
  }
}
