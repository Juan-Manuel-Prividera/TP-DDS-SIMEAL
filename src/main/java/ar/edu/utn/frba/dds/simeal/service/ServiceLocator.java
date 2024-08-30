package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.repositories.Repository;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static Map<String , Repository> repositories = new HashMap<>();

  public static void addRepository(String clave, Repository repository) {
    repositories.put(clave, repository);
  }

  public static Repository getRepository(String repositoryName) {
    return repositories.get(repositoryName);
  }
}
