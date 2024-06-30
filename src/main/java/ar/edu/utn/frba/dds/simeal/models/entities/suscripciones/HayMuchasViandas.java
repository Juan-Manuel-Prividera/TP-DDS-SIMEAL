package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HayMuchasViandas implements Notificacion {
  private Mensaje mensaje;

  public HayMuchasViandas(Heladera heladera) {
    mensaje = new Mensaje("Hay muchas viandas en la heladera: "
        + heladera.getNombre());
  }
  @Override
  public List<Colaborador> obtenerInteresados(List<Suscripcion> suscripciones, int cantidadViandas) {
    List<Colaborador> interesados = new ArrayList<>();
    for (Suscripcion suscripcion : suscripciones) {
      if(suscripcion.getCantidadViandasMaxima() <= cantidadViandas)
        interesados.add(suscripcion.getSuscriptor());
    }
    return interesados;
  }

}




