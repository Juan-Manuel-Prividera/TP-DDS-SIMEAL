package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
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

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  Heladera heladera;

  // No interesa persistir este dato ya que se obtiene en ejecucion y se
  // actualiza constantemente
  @Transient
  MedicionTemperatura ultimaTemperaturaRegistrada = null;


  public Alerta recibir(Medicion medicion) {
    if (medicion.esDeTemperatura()) ultimaTemperaturaRegistrada = (MedicionTemperatura) medicion;
    medicion.procesar(heladera);
    medicion.setSensor(this);

    return medicion.procesar(heladera);
  }

}
