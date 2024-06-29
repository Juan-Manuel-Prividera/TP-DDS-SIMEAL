package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import lombok.Getter;

import java.util.List;

@Getter
public class Rubro {
  private String nombre;
  private final List<Rubro> subRubros;

  public Rubro(String nombre, List<Rubro> subRubros) {
    this.nombre = nombre;
    this.subRubros = subRubros;
  }
}
