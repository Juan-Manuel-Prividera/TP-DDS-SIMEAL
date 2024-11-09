package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(force = true)
@Table(name = "adherir_heladera")
public class AdherirHeladera extends Persistente implements ColaboracionPuntuable {
  @ManyToOne
  @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  @Getter
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column(name = "fechaRealizacion")
  private final LocalDate fechaDeRealizacion;

  @Builder.Default
  @Transient
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
