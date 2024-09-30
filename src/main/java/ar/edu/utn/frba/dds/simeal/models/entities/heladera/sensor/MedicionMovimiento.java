package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("movimiento")
public class MedicionMovimiento extends Medicion {

  @Override
  public void procesar(Heladera heladera) {
    Alerta alerta = new Alerta(
        heladera,
        "Se detectó movimiento con la heladera cerrada.",
        TipoAlerta.ALERTA_FRAUDE
    );
    heladera.reportarIncidente(alerta);
  }

  @Override
  public boolean esDeTemperatura() {
    return false;
  }


}
