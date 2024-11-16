package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "donar_dinero")
public class DonarDinero extends Persistente implements ColaboracionPuntuable {
  @Getter
  @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column(name = "frecuencia")
  @Enumerated(EnumType.STRING)
  private Frecuencia frecuencia;

  @Column(name = "fechaRealizacion")
  private LocalDate fechaDeRealizacion;

  @Column(name = "cantDinero")
  private double cantidadDinero;

  @Builder.Default
  @Transient
  private final double factorDeReconocimiento = 0.5;

  public static DonarDinero create(Colaborador colaborador,
                                   LocalDate fechaDeRealizacion, int cantidadDinero) {
    DonarDinero donarDinero = DonarDinero.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .cantidadDinero(cantidadDinero)
        .build();
    donarDinero.getColaborador()
            .sumarPuntosReconocimiento(donarDinero.calcularReconocimientoParcial());
    return donarDinero;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return cantidadDinero * this.factorDeReconocimiento;
  }

  @Override
  public String getCantidad() {
    return "$" + cantidadDinero;
  }
}
