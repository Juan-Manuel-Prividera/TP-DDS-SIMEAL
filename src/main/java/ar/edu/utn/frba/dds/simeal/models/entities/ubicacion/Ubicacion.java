package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ubicacion {
  private String nombreCalle;
  private int altura;
  private double longitud;
  private double latitud;
}
