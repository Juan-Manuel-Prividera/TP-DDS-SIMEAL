package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import lombok.Getter;
import java.util.List;

public class IncidenteRepository {
  private List<Incidente> incidentes = null;
  @Getter
  static private IncidenteRepository instance;

  public void guardar(Incidente incidente) {
    incidentes.add(incidente);
  }

}
