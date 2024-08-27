package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Embeddable
@AllArgsConstructor
public class Coordenada {
  @Column(name = "latitud")
  private double latitud;
  @Column(name = "longitud")
  private double longitud;
}
