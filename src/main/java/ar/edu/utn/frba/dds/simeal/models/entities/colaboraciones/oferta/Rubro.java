package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import java.util.List;
import lombok.Getter;

@Getter
public class Rubro {
  private String nombre;
  private List<Rubro> subRubros;

  public Rubro(String nombre, List<Rubro> subRubros) {
    this.nombre = nombre;
    this.subRubros = subRubros;
  }
}
