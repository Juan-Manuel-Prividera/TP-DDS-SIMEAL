package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AreaDeCobertura {
  @OneToOne
  @JoinColumn(name="ubicacion_id", referencedColumnName = "id")
  private Ubicacion ubicacion;
  @Column(name = "radioCobertura")
  private Double radioDeCobertura;

  public boolean cubreEstaUbicacion(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion) <= radioDeCobertura;
  }
}
