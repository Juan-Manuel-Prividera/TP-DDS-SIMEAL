package ar.edu.utn.frba.dds.simeal.service.CalculadorCoordenadas;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

public class CalculadorCoordenadas {
  private static final CalculadorCoordenadasAdapter calculadorCoordenadasAdapter = new CalculadorCoordenadasAdapter();
  public static Coordenada calcular(Ubicacion ubicacion) {
    String provincia = ubicacion.getProvincia().toString().replace("_", " ");
    return calculadorCoordenadasAdapter
      .calcularCoordenadas(ubicacion.getNombreCalle(), ubicacion.getAltura(), ubicacion.getCodigoPostal(), provincia);
  }
}
