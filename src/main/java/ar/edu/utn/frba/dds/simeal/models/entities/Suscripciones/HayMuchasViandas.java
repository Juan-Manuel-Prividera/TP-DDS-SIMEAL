package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class HayMuchasViandas implements Suscripcion {
  private List<Suscriptor> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;

  public HayMuchasViandas(Heladera heladera) {
    suscriptores = new ArrayList<>();
    mensaje = new Mensaje("Hay muchas viandas en la heladera ubicada en: "
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
    return suscriptores.stream()
        .filter(s -> s.getCantidadCritica() <= cantidadViandas)
        .toList();
  }
}
