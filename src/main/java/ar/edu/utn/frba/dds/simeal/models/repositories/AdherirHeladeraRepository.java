package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

import java.util.List;

public class AdherirHeladeraRepository {
  private static AdherirHeladeraRepository instance;

  private AdherirHeladeraRepository() {

  }

  public static AdherirHeladeraRepository getInstance() {
    if (instance == null) {
      instance = new AdherirHeladeraRepository();
    }
    return instance;
  }
  // Deberia recibir el id
  public List<AdherirHeladeraRepository> getPorColaborador(Long id) {
    return null;
    // TODO
  }
}
}
