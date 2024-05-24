package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
