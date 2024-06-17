package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class HuboUnDesperfecto implements Suscripcion {
  private List<Suscriptor> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;

  public HuboUnDesperfecto(Heladera heladera) {
    suscriptores = new ArrayList<>();
    this.heladera = heladera;
    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera ubicada en: "
        + heladera.getUbicacion().getStringUbi());
  }

  @Override
  public void suscribir(Suscriptor suscriptor) {
    suscriptores.add(suscriptor);
  }

  @Override
  public void desuscribir(Suscriptor suscriptor) {
    suscriptores.remove(suscriptor);
  }

  @Override
  public List<Suscriptor> obtenerInteresados(int cantidadViandas) {
    return suscriptores;
  }
}
