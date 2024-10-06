package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;

public class TarjetaPersonaVulnerableRepository extends Repositorio{
  public TarjetaPersonaVulnerable getPorNumero(String codigo) {
    TarjetaPersonaVulnerable personaVulnerable;
    beginTransaction();
    personaVulnerable = entityManager()
      .createQuery(" FROM " + TarjetaPersonaVulnerable.class.getName() + " WHERE codigo= :codigo", TarjetaPersonaVulnerable.class)
      .setParameter("codigo", codigo).getResultList().get(0);
    commitTransaction();
    return personaVulnerable;
  }
}

