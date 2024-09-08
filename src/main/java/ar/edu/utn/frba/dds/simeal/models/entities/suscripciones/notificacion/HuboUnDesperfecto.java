package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.SugerenciaHeladeras;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HuboUnDesperfecto implements Notificacion {
  private int cercaniaSugerencia = 1000;
  private SugerenciaHeladeras sugerenciaHeladeras;

//  public HuboUnDesperfecto(Heladera heladera, SugerenciaHeladeras sugerenciaHeladeras) {
//    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera: "
//        + heladera.getNombre() + "\n" + sugerenciaHeladeras.getSugerencia(cercaniaSugerencia));
//  }

  @Override
  public Mensaje getMensaje(Heladera heladera) {
     return new Mensaje("Hubo un desperfecto en la heladera: "
        + heladera.getNombre() + "\n" + sugerenciaHeladeras.getSugerencia(cercaniaSugerencia));
  }

  @Override
  public Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas) {
    return tipoEvento.equals(TipoEvento.INCIDENTE);
  }

}
