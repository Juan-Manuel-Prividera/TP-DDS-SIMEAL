package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="estado_heladera")
public class EstadoHeladera extends Persistente {
  @ManyToOne
  @JoinColumn(name = "heladera_id")
  private Heladera heladera;

  @Column(name="fecha_inicio")
  private LocalDate fechaInicio;

  @Column(name="fecha_fin")
  private LocalDate fechaFin;

  @Enumerated(EnumType.STRING)
  private TipoEstadoHeladera tipo;


  public boolean disponible() {
    return tipo == TipoEstadoHeladera.ACTIVA;
  }

}
