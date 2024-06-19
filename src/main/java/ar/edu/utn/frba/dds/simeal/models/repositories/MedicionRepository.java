package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import lombok.Getter;
import java.util.List;

public class MedicionRepository {
  private List<Medicion> mediciones = null;
  @Getter
  static private MedicionRepository instance;

  public void guardar(Medicion medicion) {
    mediciones.add(medicion);
    // TODO persistir de verdad
  }

  public void eliminar(Medicion medicion) {
    // Dudosísimo...
    mediciones.remove(medicion);
  }

}
