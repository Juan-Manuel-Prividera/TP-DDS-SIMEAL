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
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.time.LocalDate;

public class AltaPersonaVulnerableController {

  public void create(TarjetaPersonaVulnerable tarjetaPersonaVulnerable, PersonaVulnerable personaVulnerable, Context ctx) {
    Colaborador colaborador = (Colaborador) ServiceLocator
      .getRepository(Repositorio.class)
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    
    // Usamos el Builder porque nos setea los puntos de reconocimiento
    DarDeAltaPersonaVulnerable darDeAltaPersonaVulnerable = (DarDeAltaPersonaVulnerable) ColaboracionBuilder
      .crearColaboracion(TipoColaboracion.ENTREGA_TARJETA, LocalDate.now(),colaborador,1);
    darDeAltaPersonaVulnerable.setTarjeta(tarjetaPersonaVulnerable);
    darDeAltaPersonaVulnerable.setPersonaVulnerable(personaVulnerable);

    ServiceLocator.getRepository(Repositorio.class).actualizar(colaborador);
    ServiceLocator.getRepository(ColaboracionRepository.class).guardar(darDeAltaPersonaVulnerable);
  }
}
