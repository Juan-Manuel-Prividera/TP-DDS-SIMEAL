package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AreaDeCobertura {
  private Ubicacion ubicacion;
  private double radioDeCobertura;

  public boolean cubreEstaUbicacion(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion) <= radioDeCobertura;
  }
}
