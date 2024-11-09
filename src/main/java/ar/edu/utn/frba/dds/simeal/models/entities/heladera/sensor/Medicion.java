package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoMedicion"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MedicionMovimiento.class, name = "medicionMovimiento"),
    @JsonSubTypes.Type(value = MedicionTemperatura.class, name = "medicionTemperatura")
})
@Entity
@Table(name = "medicion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_medicion")
public abstract class Medicion extends Persistente {
  @Getter
  @Setter
  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;
  @Setter
  @ManyToOne
  @JoinColumn(name = "sensor_id", referencedColumnName = "id")
  private Sensor sensor;

  abstract Alerta procesar(Heladera heladera);
  abstract boolean esDeTemperatura();
}
