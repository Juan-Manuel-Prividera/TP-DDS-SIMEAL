package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Getter;

import java.time.LocalDate;


public class Vianda {
  private final TipoDeComida tipoDeComida;
  private final LocalDate fechaCaducidad;
  private final LocalDate fechaDonacion;
  @Getter
  private final Colaborador colaborador;
  private int calorias;
  private Heladera heladera;
  private boolean entregada;

  public Vianda(TipoDeComida tipoDeComida, Colaborador colaborador,
                Heladera heladera, LocalDate fechaCaducidad) {
    this.tipoDeComida = tipoDeComida;
    this.fechaCaducidad = fechaCaducidad;
    this.colaborador = colaborador;
    this.fechaDonacion = LocalDate.now();
    this.heladera = heladera;
  }

  public void moverA(Heladera heladera) {
    this.heladera = heladera;
  }
  public void retirar()
  {
    this.heladera = null;
    entregada = true;
  }

}
