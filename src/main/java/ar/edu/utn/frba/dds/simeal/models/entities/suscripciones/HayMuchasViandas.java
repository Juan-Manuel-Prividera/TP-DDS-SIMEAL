package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HayMuchasViandas implements Suscripcion {
  @Setter
  private List<Suscriptor> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;

  public HayMuchasViandas(Heladera heladera) {
    suscriptores = new ArrayList<>();
    mensaje = new Mensaje("Hay muchas viandas en la heladera: "
        + heladera.getNombre());
  }

  @Override
  public void suscribir(Suscriptor suscriptor) {
    if (suscriptor.getUbicacion().estaCercaDe(heladera.getUbicacion())) {
      suscriptores.add(suscriptor);
    } else {
      throw new RuntimeException("No esta lo suficientemente cerca para suscribirse a esta heladera");
    }
  }

  @Override
  public void desuscribir(Suscriptor suscriptor) {
    suscriptores.remove(suscriptor);
  }

  @Override
  public List<Suscriptor> obtenerInteresados(int cantidadCritica) {
    return suscriptores.stream()
        .filter(s -> s.getCantidadCritica() <= cantidadCritica)
        .toList();
  }

  @Override
  public boolean esEsteTipo(String tipo) {
    return tipo.equals("ingresarA");
  }


}
