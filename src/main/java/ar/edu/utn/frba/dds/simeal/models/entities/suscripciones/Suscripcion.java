package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Suscripcion {
  private Colaborador suscriptor;
  private Heladera heladera;
  private int cercaniaNecesaria;
  private int cantidadViandasCriticas;
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
