package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Medicion {
  private LocalDateTime fechaHora;
  private String medicion;
  private final TipoMedicion tipoMedicion;

  public Medicion(TipoMedicion tipoMedicion) {
    this.tipoMedicion = tipoMedicion;
  }


}
