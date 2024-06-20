package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;

public interface Medicion {
  void procesar(Heladera heladera);
  boolean esDeTemperatura();

}
