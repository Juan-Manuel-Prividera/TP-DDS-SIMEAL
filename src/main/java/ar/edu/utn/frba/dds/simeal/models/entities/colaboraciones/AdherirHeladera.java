package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
@Builder
public class AdherirHeladera implements Colaboracion{
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  @Builder.Default
  private double factorDeReconocimiento = 5;
  private Heladera heladera;

//  public AdherirHeladera(){
//    fechaDeRealizacion = LocalDate.now();
//  }
//
  @Override
  public double calcularReconocimientoParcial() {
    Period periodoEnFuncionamiento = Period.between(this.fechaDeRealizacion, LocalDate.now());

    double cantMeses =
        periodoEnFuncionamiento.getYears()*12
          +
        periodoEnFuncionamiento.getMonths();
    return cantMeses * this.factorDeReconocimiento;
  }
}
