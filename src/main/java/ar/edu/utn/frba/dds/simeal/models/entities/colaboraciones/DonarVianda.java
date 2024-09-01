package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import lombok.*;
import net.bytebuddy.utility.nullability.MaybeNull;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "donarVianda")
public class DonarVianda implements ColaboracionPuntuable {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  @Getter
  private final Colaborador colaborador;
  @Column
  private final LocalDate fechaDeRealizacion;

  @ManyToOne
  @JoinColumn(name = "vianda_id", referencedColumnName = "id")
  private Vianda vianda;
  @Builder.Default
  private final double factorDeReconocimiento = 1.5;


  public static DonarVianda create(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    DonarVianda donarVianda = DonarVianda.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();

    donarVianda.getColaborador()
        .sumarPuntosReconocimiento(donarVianda.calcularReconocimientoParcial());
    return donarVianda;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
