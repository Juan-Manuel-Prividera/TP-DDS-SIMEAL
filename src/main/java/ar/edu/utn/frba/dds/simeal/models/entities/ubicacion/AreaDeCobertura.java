package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AreaDeCobertura {
  @Cascade({CascadeType.MERGE,CascadeType.PERSIST})
  @OneToOne
  @JoinColumn(name="ubicacion_id", referencedColumnName = "id")
  private Ubicacion ubicacion;
  @Column(name = "radioCobertura")
  private Double radioDeCobertura;


  public boolean cubreEstaUbicacion(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion) <= radioDeCobertura;
  }

  public String toString() {
    return ubicacion.getStringUbi() + " " + radioDeCobertura;
  }
}
