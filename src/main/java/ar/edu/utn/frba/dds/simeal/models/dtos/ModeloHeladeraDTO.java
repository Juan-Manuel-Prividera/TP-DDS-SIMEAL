package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ModeloHeladeraDTO {
  private String nombre;
  private Long id;

  public ModeloHeladeraDTO(ModeloHeladera modeloHeladera) {
    this.nombre = modeloHeladera.getNombre();
    this.id = modeloHeladera.getId();
  }
}
