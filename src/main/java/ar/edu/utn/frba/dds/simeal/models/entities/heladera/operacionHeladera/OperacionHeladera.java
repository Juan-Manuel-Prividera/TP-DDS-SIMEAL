package ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "operacion_heladera")
public class OperacionHeladera extends Persistente {
  @OneToOne
  @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
  private SolicitudOperacionHeladera solicitud;
  @Column(name = "horaRealizacion")
  private LocalDateTime horaDeRealizacion;
}
