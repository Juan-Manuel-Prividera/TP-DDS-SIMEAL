package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vianda {
  private final TipoDeComida tipoDeComida;
  private final LocalDate fechaCaducidad;
  private final LocalDate fechaDonacion;
  private Colaborador colaborador;
  private int calorias;
  private Heladera heladera;

  public Vianda(TipoDeComida tipoDeComida, LocalDate fechaCaducidad, Heladera heladera) {
    this.tipoDeComida = tipoDeComida;
    this.fechaCaducidad = fechaCaducidad;
    this.fechaDonacion =LocalDate.now();
    this.heladera = heladera;
  }

  public void moverA(Heladera heladera) {
    this.heladera = heladera;
  }

}
