package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
public class DonarDinero implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final Frecuencia frecuencia;
  private final LocalDate fechaDeRealizacion;
  private double cantidadDinero;
  @Builder.Default
  private final double factorDeReconocimiento = 0.5;



  @Override
  public double calcularReconocimientoParcial() {
    return cantidadDinero * this.factorDeReconocimiento;
  }
}
