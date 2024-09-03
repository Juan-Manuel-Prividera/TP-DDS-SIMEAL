package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceLocator {
  private static final Map<TipoRepo , Repository<?>> repositories = new HashMap<>();

  static {

    repositories.put(TipoRepo.HELADERA, new RepositoryGenerico<Heladera>(Heladera.class));
  }

  public static void addRepository(TipoRepo tipoRepo, Repository repository) {
    repositories.put(tipoRepo, repository);
  }

  public static Repository getRepository(TipoRepo repositoryName) {
    return repositories.get(repositoryName);
  }
}
