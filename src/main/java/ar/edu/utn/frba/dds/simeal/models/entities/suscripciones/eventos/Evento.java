package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos;

import ar.edu.utn.frba.dds.simeal.converters.NotificacionConverter;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.Notificacion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "evento")
public class Evento extends Persistente {
  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private final Heladera heladeraAfectada;

  @Column(name = "tipoEvento")
  @Enumerated(EnumType.STRING)
  private final TipoEvento tipoEvento;

  @Column(name = "fechaDeOcurrencia")
  private final LocalDateTime fechaDeOcurrencia;

  @Convert(converter =  NotificacionConverter.class)
  @Column(name = "notificacion")
  private final Notificacion notificacion;

  public Evento(Heladera heladera, TipoEvento tipoEvento, Notificacion notificacion) {
    this.heladeraAfectada = heladera;
    this.tipoEvento = tipoEvento;
    this.fechaDeOcurrencia = LocalDateTime.now();
    this.notificacion = notificacion;
  }
}
