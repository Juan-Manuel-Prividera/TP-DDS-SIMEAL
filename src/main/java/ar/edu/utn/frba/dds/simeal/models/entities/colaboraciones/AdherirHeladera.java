package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import java.time.Period;
import lombok.Builder;
import lombok.Getter;


@Builder
public class AdherirHeladera implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  @Builder.Default
  private final double factorDeReconocimiento = 5;
  private final Heladera heladera;

  public static AdherirHeladera create(Colaborador colaborador, LocalDate fecha) {
    return AdherirHeladera.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fecha)
        .build();
  }


  @Override
  public double calcularReconocimientoParcial() {
    Period periodoEnFuncionamiento = Period.between(this.fechaDeRealizacion, LocalDate.now());

    double cantMeses =
        periodoEnFuncionamiento.getYears() * 12
          +
        periodoEnFuncionamiento.getMonths();
    return cantMeses * this.factorDeReconocimiento;
  }
}
