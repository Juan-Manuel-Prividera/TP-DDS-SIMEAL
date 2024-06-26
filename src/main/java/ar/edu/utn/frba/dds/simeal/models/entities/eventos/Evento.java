package ar.edu.utn.frba.dds.simeal.models.entities.eventos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Evento {
  private Heladera heladeraAfectada;
  private TipoEvento tipoEvento;
  private LocalDateTime fechaDeOcurrencia;

  public Evento(Heladera heladera, TipoEvento tipoEvento) {
    this.heladeraAfectada = heladera;
    this.tipoEvento = tipoEvento;
    this.fechaDeOcurrencia = LocalDateTime.now();
  }
}
