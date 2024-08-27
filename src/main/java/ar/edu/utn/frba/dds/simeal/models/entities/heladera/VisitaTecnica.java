package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name="visita_tecnica")
@Getter
@NoArgsConstructor
public class VisitaTecnica extends Persistente {
  @ManyToOne
  @JoinColumn(referencedColumnName = "id", name = "heladera_id")
  private Heladera heladera;

  @Column(name="descripcion")
  private String descripcion;

  @Column(name="fecha_hora")
  private LocalDateTime fechaHora;

  @Column(name = "exitosa")
  private Boolean exitosa;

  @Column(name = "imagen")
  private String imagen;
}
