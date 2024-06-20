package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter @RequiredArgsConstructor
public class OperacionSobreHeladera {
  private final TipoOperacion tipoOperacion;
  private final Heladera heladera;
  private final int cantViandas;
  private final LocalDateTime horaInicio;
  private Integer horasParaEjecucion = 3;

  public LocalDateTime horaFinalPermisos(){
    return this.horaInicio.plusHours(this.horasParaEjecucion);
  }
  public String descripcion(){
    return this.tipoOperacion.toString()
              + " de vianda/s en heladera: "
              + this.heladera.getNombre()
              + "; desde: " + this.horaInicio.toString()
              + ", hasta: " + this.horaFinalPermisos().toString();
  }

  public boolean puedeEjecutarse(Heladera heladera){
    return LocalDateTime.now().isBefore(this.horaFinalPermisos()) & this.heladera == heladera;
  }
}
