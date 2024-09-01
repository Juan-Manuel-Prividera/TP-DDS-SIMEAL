package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class TipoDeComida {
  private String descripcion;
}
