package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RubroDTO {
  private Long id;
  private String rubro;

  public RubroDTO(Rubro rubro) {
    this.id = rubro.getId();
    this.rubro = rubro.getNombre();
  }
}
