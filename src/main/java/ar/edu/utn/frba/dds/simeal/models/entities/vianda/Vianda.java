package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.Evento;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vianda {
  private TipoDeComida tipoDeComida;
  private LocalDate fechaCaducidad;
  private LocalDate fechaDonacion;
  private Colaborador colaborador;
  private int calorias;
  private Heladera heladera;
  private boolean entregada;

  private AdministradorDeEventos administradorDeEventos;

  public Vianda(Heladera heladera, AdministradorDeEventos administradorDeEventos) {
    this.heladera = heladera;
    this.administradorDeEventos = administradorDeEventos;
  }



  public void moverA(Heladera heladera) {
    administradorDeEventos.huboUnEvento(new Evento(this.heladera, TipoEvento.RETIRO));
    ingresarA(heladera);
  }

  public void retirar() {
    // Esto avisa cuando se retira una vianda de una heladera
    administradorDeEventos.huboUnEvento(new Evento(this.heladera, TipoEvento.RETIRO));
    this.heladera = null;
    entregada = true;
  }

  private void ingresarA(Heladera heladera) {
    // Esto avisa cuando se ingresa una vianda a una heladera
    administradorDeEventos.huboUnEvento(new Evento(heladera, TipoEvento.INGRESO));
    this.heladera = heladera;
  }


}
