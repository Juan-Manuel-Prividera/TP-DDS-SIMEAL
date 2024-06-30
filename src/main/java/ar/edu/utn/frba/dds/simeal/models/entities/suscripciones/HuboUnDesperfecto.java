package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Getter;

import java.util.List;

@Getter
public class HuboUnDesperfecto implements Notificacion {
  private Mensaje mensaje;
  private SugerenciaHeladeras sugerenciaHeladeras;
  private int cercaniaSugerencia = 1000;


  public HuboUnDesperfecto(Heladera heladera) {
    sugerenciaHeladeras = new SugerenciaHeladeras(heladera.getUbicacion());
    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera: "
        + heladera.getNombre() + "\n" + sugerenciaHeladeras.getSugerencia(cercaniaSugerencia));
  }

  @Override
  public List<Colaborador> obtenerInteresados(List<Suscripcion> suscripciones, int cantidadViandas) {
    return suscripciones.stream().map(Suscripcion::getSuscriptor).toList();
  }


}
