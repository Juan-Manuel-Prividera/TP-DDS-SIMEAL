package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuedanPocasViandas implements Notificacion {
  private Mensaje mensaje;

  public QuedanPocasViandas(Heladera heladera) {
    mensaje = new Mensaje("Quedan pocas viandas en la heladera: "
        + heladera.getNombre());
  }

  @Override
  public List<Colaborador> obtenerInteresados(List<Suscripcion> suscripciones, int cantidadViandas) {
    List<Colaborador> interesados = new ArrayList<>();
    for (Suscripcion suscripcion : suscripciones) {
      if(suscripcion.getCantidadViandasMinimas() >= cantidadViandas)
        interesados.add(suscripcion.getSuscriptor());
    }
    return interesados;
  }
}
