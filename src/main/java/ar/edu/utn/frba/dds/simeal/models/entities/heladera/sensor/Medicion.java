package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Medicion {
  private LocalDate fechaMedicion;
  private String medicion;
  private Boolean medicionCritica;
  private TipoMedicion tipoMedicion;

  public boolean generaAlerta() {
    return medicionCritica;
  }


}
