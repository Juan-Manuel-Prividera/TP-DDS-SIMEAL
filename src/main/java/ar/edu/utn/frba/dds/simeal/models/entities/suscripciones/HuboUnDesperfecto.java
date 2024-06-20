package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HuboUnDesperfecto implements Suscripcion {
  @Setter
  private List<Suscriptor> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;

  public HuboUnDesperfecto(Heladera heladera) {
    suscriptores = new ArrayList<>();
    this.heladera = heladera;
    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera: "
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
    return suscriptores;
  }

  @Override
  public boolean esEsteTipo(String tipo) {
    return tipo.equals("incidente");

  }
}
