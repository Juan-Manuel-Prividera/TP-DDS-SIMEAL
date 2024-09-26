package ar.edu.utn.frba.dds.simeal.config;

import ar.edu.utn.frba.dds.simeal.models.repositories.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static final Map<TipoRepo , Repositorio> repositories = new HashMap<>();
  // TODO: Ver de hacer como hizo el profe para no tener el bloque estatico
  static {
    repositories.put(TipoRepo.COLABORACION, new ColaboracionRepository());
    repositories.put(TipoRepo.SUSCRIPCION, new SuscripcionesRepository());
    repositories.put(TipoRepo.MODELO_HELADERA, new ModeloHeladeraRepository());
    repositories.put(TipoRepo.HELADERA, new Repositorio());
    repositories.put(TipoRepo.VIANDA, new ViandaRepository());
    repositories.put(TipoRepo.SENSOR, new SensorRepository());
    repositories.put(TipoRepo.COLABORADOR, new Repositorio());
  }

  public static void addRepository(TipoRepo tipoRepo, Repositorio repository) {
    repositories.put(tipoRepo, repository);
  }

  public static Repositorio getRepository(TipoRepo repositoryName) {
    if (!repositories.containsKey(repositoryName)) {
      return new Repositorio();
    } else {
      return repositories.get(repositoryName);
    }
  }

}
