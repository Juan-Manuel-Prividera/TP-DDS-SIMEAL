package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Desperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AdministradorAlertas {
  private Heladera heladera;

  public void responderAlerta(Medicion alerta) {
    String descripcion = alerta.getMedicion() + " " + alerta.getFechaMedicion() + " "
        + alerta.getTipoMedicion();

    heladera.cambiarDeEstado(new Inactiva());
    heladera.agregarDesperfecto(new Desperfecto(descripcion, alerta));
    heladera.enviarNotificacionPorDefecto(descripcion);
  }
}
