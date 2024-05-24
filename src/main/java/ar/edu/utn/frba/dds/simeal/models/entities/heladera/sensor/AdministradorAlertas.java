package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;

import java.util.List;

public class AdministradorAlertas {
  private Heladera heladera;

  public void responderAlerta(Medicion alerta) {
    heladera.cambiarDeEstado(new Inactiva());
  }
}
