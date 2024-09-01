package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;


@Builder
public class AdherirHeladera implements ColaboracionPuntuable {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  @Getter
  private final Colaborador colaborador;
  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private final Heladera heladera;

  @Column
  private final LocalDate fechaDeRealizacion;
  @Builder.Default
  private final double factorDeReconocimiento = 5;

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
