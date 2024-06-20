package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TarjetaColaborador {
  List<OperacionSobreHeladera> operaciones = new ArrayList<>();

  public boolean puedeAcceder(Heladera heladera){
    return !operaciones.isEmpty() && operaciones.stream().anyMatch(o -> o.puedeEjecutarse(heladera));
  }

  public void addOperacion(OperacionSobreHeladera operacion){
    this.operaciones.add(operacion);
  }
}
