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
  private final int calorias;
  private Heladera heladera;

  public Vianda(TipoDeComida tipoDeComida, LocalDate fechaCaducidad,
                LocalDate fechaDonacion, int calorias, Heladera heladera) {

    this.tipoDeComida = tipoDeComida;
    this.fechaCaducidad = fechaCaducidad;
    this.fechaDonacion = fechaDonacion;
    this.calorias = calorias;
    this.heladera = heladera;
  }

  public void moverA(Heladera heladera) {
    this.heladera = heladera;
  }

}
