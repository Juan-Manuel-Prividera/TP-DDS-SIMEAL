package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
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
@Getter
@Entity
@Table(name = "distribuir_vianda")
public class DistribuirVianda extends Persistente implements ColaboracionPuntuable {
  @ManyToOne @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "origen_id", referencedColumnName = "id")
  private Heladera origen;

  @ManyToOne
  @JoinColumn(name = "destino_id", referencedColumnName = "id")
  private Heladera destino;

  @Column(name = "motivo")
  @Enumerated(EnumType.STRING)
  private Motivo motivo;

  @Column(name = "cantViandas")
  private int cantidadViandasMover;

  @Column(name = "fechaDeRealizacion")
  private final LocalDate fechaDeRealizacion;

  @Builder.Default
  @Transient
  private final double factorDeReconocimiento = 1;


  public static DistribuirVianda create(Colaborador colaborador,
                                        LocalDate fechaDeRealizacion, int cantidadViandasMover) {
    DistribuirVianda distribuirVianda = DistribuirVianda.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .cantidadViandasMover(cantidadViandasMover)
        .build();
    distribuirVianda.getColaborador()
        .sumarPuntosReconocimiento(distribuirVianda.calcularReconocimientoParcial());


    return distribuirVianda;

  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.cantidadViandasMover * factorDeReconocimiento;
  }

  @Override
  public String getCantidad() {
    return cantidadViandasMover + "";
  }

}
