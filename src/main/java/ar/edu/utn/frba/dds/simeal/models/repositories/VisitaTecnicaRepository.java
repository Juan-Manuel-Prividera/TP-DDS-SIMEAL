package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class VisitaTecnicaRepository {
  private List<VisitaTecnica> visitas;
  static private VisitaTecnicaRepository instance;

  public static VisitaTecnicaRepository getInstance() {
    if(instance == null)
      return new VisitaTecnicaRepository();
    return instance;
  }

  private VisitaTecnicaRepository() {
    visitas = new ArrayList<>();
  }

  public void guardar(VisitaTecnica visita) {
    visitas.add(visita);
  }

  public void eliminar(VisitaTecnica visita) {
    visitas.remove(visita);
  }


}
