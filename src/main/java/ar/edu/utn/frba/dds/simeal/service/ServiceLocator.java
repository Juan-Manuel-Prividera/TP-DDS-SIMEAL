package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.repositories.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static Map<String , Repository> repositories = new HashMap<>();

  static {
    repositories.put("heladera", new HeladeraRepository());
    repositories.put("suscripciones", new SuscripcionesRepository());
    repositories.put("viandas", new ViandaRepository());
    repositories.put("sensores", new SensorRepository());
  }

  public static void addRepository(String clave, Repository repository) {
    repositories.put(clave, repository);
  }

  public static Repository getRepository(String repositoryName) {
    return repositories.get(repositoryName);
  }
}
