package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Medicion {
  private LocalDate fechaMedicion;
  private String medicion;
  private Boolean medicionCritica;
  private final TipoMedicion tipoMedicion;

  public Medicion(TipoMedicion tipoMedicion) {
    this.tipoMedicion = tipoMedicion;
  }

  public boolean generaAlerta() {
    return medicionCritica;
  }


}
