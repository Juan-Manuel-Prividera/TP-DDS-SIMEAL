package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class Suscripcion {
  private Colaborador suscriptor;
  private Heladera heladera;
  private int cercaniaNecesaria;
  private int cantidadViandasMinimas;
  private int cantidadViandasMaxima;
  private List<TipoEvento> eventosDeInteres;

  public Suscripcion(Heladera heladera, Colaborador suscriptor, int min, int max){
    this.heladera = heladera;
    this.cercaniaNecesaria = 1000;
    this.suscriptor = suscriptor;
    this.cantidadViandasMinimas = min;
    this.cantidadViandasMaxima = max;
  }
  public boolean puedeSuscribirse(Colaborador colaborador) {
    return colaborador.getUbicacion().estaCercaDe(heladera.getUbicacion(),cercaniaNecesaria);
  }
  public boolean interesaEsteEvento(TipoEvento tipoEvento) {
    return eventosDeInteres.contains(tipoEvento);
  }
}
