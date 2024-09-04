package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AreaDeCobertura {
  @Embedded
  private Ubicacion ubicacion;
  @Column(name = "radioCobertura")
  private Double radioDeCobertura;

  public boolean cubreEstaUbicacion(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion) <= radioDeCobertura;
  }
}
