package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Localidad {
  private String nombre;
  private Provincia provincia;
}
