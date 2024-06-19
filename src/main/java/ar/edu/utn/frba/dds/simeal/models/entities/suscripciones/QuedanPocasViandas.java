package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QuedanPocasViandas implements Suscripcion {
  private Heladera heladera;
  @Setter
  private List<Suscriptor> suscriptores;
  private Mensaje mensaje;

  public QuedanPocasViandas(Heladera heladera) {
    mensaje = new Mensaje("Quedan pocas viandas en la heladera: "
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

  // Este metodo diferencia cada suscripcion
  @Override
  public List<Suscriptor> obtenerInteresados(int cantidadCritica) {
    return suscriptores.stream()
        .filter(s -> s.getCantidadCritica() <= cantidadCritica)
        .toList();
  }

  @Override
  public boolean esEsteTipo(String tipo) {
    return tipo.equals("retirar");

  }
}
