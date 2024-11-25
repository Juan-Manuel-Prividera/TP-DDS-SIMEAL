package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "donar_vianda")
@Getter
public class DonarVianda extends Persistente implements ColaboracionPuntuable {
  @ManyToOne
  @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column(name = "fechaDeRealizacion")
  private LocalDate fechaDeRealizacion;

  @OneToOne(cascade = CascadeType.PERSIST)
  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @JoinColumn(name = "vianda_id", referencedColumnName = "id")
  private Vianda vianda;

  @Builder.Default
  @Transient
  private double factorDeReconocimiento = 1.5;

  public DonarVianda(Colaborador colaborador, LocalDate fecha, Vianda vianda) {
   this.colaborador = colaborador;
   this.fechaDeRealizacion = fecha;
   this.vianda = vianda;
  }


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

  @Override
  public String getCantidad() {
    return "1";
  }
}
