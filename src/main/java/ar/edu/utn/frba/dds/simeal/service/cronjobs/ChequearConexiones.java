package ar.edu.utn.frba.dds.simeal.service.cronjobs;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;

import java.time.LocalDateTime;
import java.util.List;

// Crontab 5 minutos.
// El comando recibe por parámetro un solo número, la cantidad máxima de minutos sin respuesta.
// Eso, es el 5 que está al final del comando
//  */5 * * * * "java -jar /path/to/ChequearConexionesCronjob.jar 5"

public class ChequearConexiones {
  public static void main(String[] argv) {
    List<Sensor> sensores = (List<Sensor>) ServiceLocator.getRepository(TipoRepo.SENSOR).obtenerTodos(Sensor.class);

    int maxMinutosSinRespuesta = Integer.parseInt(argv[1]);
    // Asumo que lo que tarda Java en ejecutar el archivo es despreciable en comparanción a los 5 mins.
    LocalDateTime limiteRespuesta = LocalDateTime.now().minusMinutes(maxMinutosSinRespuesta);

    for (Sensor sensor : sensores) {
      LocalDateTime horaUltimaMedicion = sensor.getUltimaTemperaturaRegistrada().getFechaHora();
      Heladera heladera = sensor.getHeladera();
      if (horaUltimaMedicion.isBefore(limiteRespuesta)) {
        Alerta alerta = new Alerta(
            heladera,
            "No se ha recibido la temperatura de la heladera en los últimos "
                + maxMinutosSinRespuesta + " minutos.",
            TipoAlerta.ALERTA_FALLA_CONEXION
        );
        heladera.reportarIncidente(alerta);
      }
    }
  }
}
