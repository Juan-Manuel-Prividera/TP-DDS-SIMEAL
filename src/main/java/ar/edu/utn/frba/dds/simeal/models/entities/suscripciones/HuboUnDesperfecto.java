package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;

@Getter
public class HuboUnDesperfecto implements Notificacion {
  private Mensaje mensaje;
  private int cercaniaSugerencia = 1000;


  public HuboUnDesperfecto(Heladera heladera, SugerenciaHeladeras sugerenciaHeladeras) {
    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera: "
        + heladera.getNombre() + "\n" + sugerenciaHeladeras.getSugerencia(cercaniaSugerencia));
  }

  @Override
  public Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas) {
    return tipoEvento.equals(TipoEvento.INCIDENTE);
  }

}
