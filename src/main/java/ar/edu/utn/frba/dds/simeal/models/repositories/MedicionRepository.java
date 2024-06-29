package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import com.itextpdf.text.pdf.PRAcroForm;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MedicionRepository {
  private List<Medicion> mediciones;
  static private MedicionRepository instance;

  public static MedicionRepository getInstance() {
    if (instance == null)
      return new MedicionRepository();
    return instance;
  }

  private MedicionRepository(){
    mediciones = new ArrayList<>();
  }

  public void guardar(Medicion medicion) {
    mediciones.add(medicion);
    // TODO persistir de verdad
  }

  public void eliminar(Medicion medicion) {
    mediciones.remove(medicion);
  }

}
