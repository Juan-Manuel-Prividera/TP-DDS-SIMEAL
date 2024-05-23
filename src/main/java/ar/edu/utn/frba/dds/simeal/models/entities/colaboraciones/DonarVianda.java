package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Vianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DonarVianda implements Colaboracion{
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private Vianda vianda;
  private double factorDeReconocimiento = 1.5;

  @Override
  public double calcularReconocimientoParcial(){
    return this.factorDeReconocimiento;
  }
}
