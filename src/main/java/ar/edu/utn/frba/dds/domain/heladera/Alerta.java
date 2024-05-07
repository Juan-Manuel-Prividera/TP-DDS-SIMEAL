package ar.edu.utn.frba.dds.domain.heladera;

import ar.edu.utn.frba.dds.domain.heladera.sensor.Sensor;
import java.time.LocalDate;

public class Alerta {
  private String mensajeAlerta;
  private LocalDate fechaDeOcurrencia;
  private Sensor sensorQueDisparo;
}
