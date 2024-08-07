package ar.edu.utn.frba.dds.simeal.models.entities.operacionHeladera;


import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Builder;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SolicitudOperacionHeladera {
  private TipoOperacion tipoOperacion;
  private TarjetaColaborador tarjetaColaborador;
  private Heladera heladera;
  private int cantViandas, horasParaEjecutarse = 3;
  private LocalDateTime horaInicio;

  //Por alguna razón, si ejecutas el metodo al mismo tiempo en el que creaste la solicitud, devuelve false
  public boolean puedeEjecutarse(Heladera heladera){
    return heladera == this.heladera && LocalDateTime.now().isBefore(this.horaInicio.plusHours(this.horasParaEjecutarse));
  }
}
