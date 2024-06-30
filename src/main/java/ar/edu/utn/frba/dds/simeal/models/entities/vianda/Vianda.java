package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.Evento;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.QuedanPocasViandas;
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
    // Cuando se hace una distribución primero se crea el evento del retiro y luego el evento del ingreso
    administradorDeEventos.huboUnEvento(new Evento(this.heladera, TipoEvento.RETIRO, new QuedanPocasViandas(this.heladera)));
    ingresarA(heladera);
  }

  public void retirar() {
    // Esto avisa cuando se retira una vianda de una heladera
    administradorDeEventos.huboUnEvento(new Evento(this.heladera, TipoEvento.RETIRO, new QuedanPocasViandas(this.heladera)));
    this.heladera = null;
    entregada = true;
  }

  public void ingresarA(Heladera heladera) {
    // Esto avisa cuando se ingresa una vianda a una heladera
    administradorDeEventos.huboUnEvento(new Evento(heladera, TipoEvento.INGRESO, new HayMuchasViandas(heladera)));
    this.heladera = heladera;
  }


}
