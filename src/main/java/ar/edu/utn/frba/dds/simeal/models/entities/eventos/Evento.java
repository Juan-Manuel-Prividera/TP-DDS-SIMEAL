package ar.edu.utn.frba.dds.simeal.models.entities.eventos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Notificacion;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Evento {
  private final Heladera heladeraAfectada;
  private final TipoEvento tipoEvento;
  private final LocalDateTime fechaDeOcurrencia;
  private final Notificacion notificacion;

  public Evento(Heladera heladera, TipoEvento tipoEvento, Notificacion notificacion) {
    this.heladeraAfectada = heladera;
    this.tipoEvento = tipoEvento;
    this.fechaDeOcurrencia = LocalDateTime.now();
    this.notificacion = notificacion;
  }
}
