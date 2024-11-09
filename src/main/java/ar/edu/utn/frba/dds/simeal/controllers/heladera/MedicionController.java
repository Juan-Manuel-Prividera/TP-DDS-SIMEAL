package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.creacionales.MedicionFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.time.LocalDateTime;

public class MedicionController {
  private SensorRepository sensorRepository;
  private IncidenteController incidenteController;
  private Repositorio repositorio;

  public MedicionController(SensorRepository sensorRepository, IncidenteController incidenteController, Repositorio repositorio) {
    this.sensorRepository = sensorRepository;
    this.incidenteController = incidenteController;
    this.repositorio = repositorio;
  }

  public void crearMedicion(String heladeraId, String tipoMedicion, String medicion) {
    Medicion medicionRecibida = MedicionFactory.crearMedicion(tipoMedicion,medicion);
    if (medicionRecibida == null) {
      Logger.error("La medicion recibida es nula :(");
      return;
    }

    Sensor sensor = sensorRepository.buscarPorHeladera(Long.valueOf(heladeraId));
    medicionRecibida.setSensor(sensor);
    medicionRecibida.setFechaHora(LocalDateTime.now());
    repositorio.guardar(medicionRecibida);
    // El procesamiento de cada tipo de medicion, desactivar la heladera,...
    Alerta alerta = sensor.recibir(medicionRecibida);

    // Si la alerta es null esta todo bien en la medicion no hacemos nada (NO es un Incidente)
    if (alerta != null) {
      // Crear incidente y avisar a tenico
      incidenteController.create(alerta);
    }
  }

}
