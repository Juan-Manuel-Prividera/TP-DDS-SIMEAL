package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import java.time.LocalDateTime;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

interface Medicion {
  void procesar(Heladera heladera);
  boolean esDeTemperatura();

}
