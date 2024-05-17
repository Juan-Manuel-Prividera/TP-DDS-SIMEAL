package ar.edu.utn.frba.dds.domain.heladera.sensor;

import ar.edu.utn.frba.dds.domain.heladera.AdministradorAlertas;
import ar.edu.utn.frba.dds.domain.heladera.Medicion;
import java.util.List;

public class Sensor {
  private List<Medicion> mediciones;
  private TipoSensor tipoSensor;
  private AdministradorAlertas administradorAlertas;
}
