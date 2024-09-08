package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class TipoDeComida {
  @Column(name = "tipoComida")
  private String descripcion;
}
