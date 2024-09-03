package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Entity
@Table(name = "distribuirVianda")
public class DistribuirVianda implements ColaboracionPuntuable {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private final Colaborador colaborador;
  @ManyToOne
  @JoinColumn(name = "origen_id", referencedColumnName = "id")
  private Heladera origen;
  @ManyToOne
  @JoinColumn(name = "destino_id", referencedColumnName = "id")
  private Heladera destino;
  @Column(name = "motivo")
  private Motivo motivo;
  @Column(name = "cantViandas")
  private int cantidadViandasMover;

  @Column()
  private final LocalDate fechaDeRealizacion;
  @Builder.Default
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

}
