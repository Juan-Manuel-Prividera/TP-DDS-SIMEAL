package ar.edu.utn.frba.dds.simeal.service.broker;


import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperMedicion {
  //private String nombreHeladera;
  private Long heladera_id;
  private Medicion medicion;

}
