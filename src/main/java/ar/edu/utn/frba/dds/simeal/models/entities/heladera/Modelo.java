package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Modelo {
  private final double temperaturaMax;
  private final double temperaturaMin;
  private final int capacidadMax;


}
