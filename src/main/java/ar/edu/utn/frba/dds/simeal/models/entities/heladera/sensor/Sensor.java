package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "sensores")
@NoArgsConstructor
@AllArgsConstructor
public class Sensor extends Persistente {
  @OneToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  Heladera heladera;

  @Transient
  MedicionTemperatura ultimaTemperaturaRegistrada = null;


  public void recibir(Medicion medicion) {
    if (medicion.esDeTemperatura()) ultimaTemperaturaRegistrada = (MedicionTemperatura) medicion;
    medicion.procesar(heladera);
  }

}
