package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("temperatura")
public class MedicionTemperatura extends Medicion{
  @Column(name = "temperatura_medida")
  private Double temperaturaMedida;

  @Override
  public void procesar(Heladera heladera) {
    if (!heladera.temperaturaAdecuada(temperaturaMedida)) {
      Alerta alerta = new Alerta(
          heladera,
          "La tempertura de la heladera excede los límites del modelo.",
          TipoAlerta.ALERTA_TEMPERATURA
      );
      heladera.reportarIncidente(alerta);
    }
  }
  @Override
  public boolean esDeTemperatura() {
    return true;
  }
}
