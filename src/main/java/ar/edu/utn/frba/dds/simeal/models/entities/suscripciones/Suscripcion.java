package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.converters.NotificacionConverter;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.Notificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscripcion")
public class Suscripcion extends Persistente {
  @ManyToOne
  @JoinColumn(name = "suscriptor_id", referencedColumnName = "id")
  private Colaborador suscriptor;

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column(name = "cercaniaNecesaria")
  private int cercaniaNecesaria;

  @Column(name = "cantViandasCritircas")
  private int cantidadViandasCriticas;

  @Convert(converter = NotificacionConverter.class)
  @Column(name = "notificacion")
  private Notificacion notificacion;

  public Suscripcion(Heladera heladera, Colaborador suscriptor, int cantidadViandas, Notificacion notificacion){
    this.heladera = heladera;
    this.cercaniaNecesaria = 1000;
    this.suscriptor = suscriptor;
    this.cantidadViandasCriticas = cantidadViandas;
    this.notificacion = notificacion;
  }

  public boolean puedeSuscribirse(Colaborador colaborador) {
    return colaborador.getUbicacion().estaCercaDe(heladera.getUbicacion(),cercaniaNecesaria);
  }

  public boolean interesaEsteEvento(TipoEvento tipoEvento, int cantidadViandas) {
    return notificacion.interesaEsteEvento(tipoEvento, this, cantidadViandas);
  }
}
