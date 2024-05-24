package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donarDinero;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class DonarDinero implements Colaboracion {
  private Colaborador colaborador;
  private Frecuencia frecuencia;
  private LocalDate fechaDeRealizacion;
  private double cantidadDinero;
  @Builder.Default
  private double factorDeReconocimiento = 0.5;



  @Override
  public double calcularReconocimientoParcial() {
    return cantidadDinero * this.factorDeReconocimiento;
  }
}
