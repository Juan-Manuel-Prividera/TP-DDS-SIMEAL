package ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "operacionHeladera")
public class OperacionHeladera {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @OneToOne
  @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
  private SolicitudOperacionHeladera solicitud;
  @Column(name = "horaRealizacion")
  private LocalDateTime horaDeRealizacion;
}
