package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter
public class Sensor {
  Heladera heladera;
  Medicion ultimaTemperaturaRegistrada = null;

  public Sensor(Heladera _heladera) {
    this.heladera = _heladera;
  }

  public void recibir(Medicion medicion) {
    if (medicion.getTipoMedicion() == TipoMedicion.MEDICION_TEMPERATURA) ultimaTemperaturaRegistrada = medicion;

    // Persistir la medición

    procesar(medicion);
  }

  private void procesar(Medicion medicion) {
    switch (medicion.getTipoMedicion()) {
      case MEDICION_TEMPERATURA:
        if (heladera.temperaturaAdecuada(Double.parseDouble(medicion.getMedicion()))) {
          Alerta alerta = new Alerta(
              heladera,
              "La tempertura de la heladera excede los límites del modelo.",
              LocalDateTime.now(),
              TipoAlerta.ALERTA_TEMPERATURA
          );
          heladera.reportarIncidente(alerta);
        }
        break;

      case MEDICION_MOVIMIENTO:
        Alerta alerta = new Alerta(
            heladera,
            "Se detectó movimiento con la heladera cerrada.",
            LocalDateTime.now(),
            TipoAlerta.ALERTA_FRAUDE
        );
        heladera.reportarIncidente(alerta);
        break;
    }

  }

}
