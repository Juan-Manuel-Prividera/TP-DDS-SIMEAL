package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import lombok.Getter;
import java.util.List;

public class VisitaTecnicaRepository {
  private List<VisitaTecnica> visitas = null;
  @Getter
  static private VisitaTecnicaRepository instance;

  public void guardar(VisitaTecnica visita) {
    visitas.add(visita);
  }

  public void eliminar(VisitaTecnica visita) {
    visitas.remove(visita);
  }


}
