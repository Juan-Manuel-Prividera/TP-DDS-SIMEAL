package ar.edu.utn.frba.dds.simeal.models.creacionales;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionMovimiento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;

public class MedicionFactory {

  public static Medicion crearMedicion(String tipoMedicion, String medicion) {
    if(tipoMedicion.equals("TEMPERATURA")) {
      return new MedicionTemperatura(Double.parseDouble(medicion));
    } else if (tipoMedicion.equals("FRAUDE")) {
      return new MedicionMovimiento();
    } else {
      return null;
    }
  }
}
