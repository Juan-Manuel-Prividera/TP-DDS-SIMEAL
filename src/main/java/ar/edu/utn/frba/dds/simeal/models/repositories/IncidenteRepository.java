package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IncidenteRepository {
  @Getter
  private List<Incidente> incidentes;
  static private IncidenteRepository instance;

  public static IncidenteRepository getInstance() {
    if(instance == null)
      return new IncidenteRepository();

    return instance;
  }

  private IncidenteRepository() {
    incidentes = new ArrayList<>();
  }

  public void guardar(Incidente incidente) {
    incidentes.add(incidente);
  }

}
