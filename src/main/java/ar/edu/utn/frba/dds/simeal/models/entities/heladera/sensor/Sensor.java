package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.repositories.MedicionRepository;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import lombok.Getter;
@Getter
public class Sensor {
  Heladera heladera;
  MedicionTemperatura ultimaTemperaturaRegistrada = null;

  public Sensor(Heladera _heladera) {
    this.heladera = _heladera;
  }

  public void recibir(Medicion medicion) {
    if (medicion.esDeTemperatura()) ultimaTemperaturaRegistrada = (MedicionTemperatura) medicion;
    medicion.procesar(heladera);
  }

}
