package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import lombok.Getter;

import java.time.LocalDateTime;

public class MedicionTemperatura implements Medicion{
  @Getter
  LocalDateTime fechaHora;
  Double temperaturaMedida;
  @Override
  public void procesar(Heladera heladera) {
    if (!heladera.temperaturaAdecuada(temperaturaMedida)) {
      Alerta alerta = new Alerta(
          heladera,
          "La tempertura de la heladera excede los límites del modelo.",
          TipoAlerta.ALERTA_TEMPERATURA
      );
      heladera.reportarIncidente(alerta);
    }
  }
  @Override
  public boolean esDeTemperatura() {
    return true;
  }
}
