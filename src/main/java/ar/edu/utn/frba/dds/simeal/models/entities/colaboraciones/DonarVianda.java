package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class DonarVianda implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private Vianda vianda;
  @Builder.Default
  private double factorDeReconocimiento = 1.5;


  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
