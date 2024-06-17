package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class QuedanPocasViandas implements Suscripcion {
  private Heladera heladera;
  private List<Suscriptor> suscriptores;
  private Mensaje mensaje;

  public QuedanPocasViandas(Heladera heladera) {
    suscriptores = new ArrayList<>();
    mensaje = new Mensaje("Quedan pocas viandas en la heladera ubicada en: "
        + heladera.getUbicacion().getStringUbi());
  }

  @Override
  public void suscribir(Suscriptor suscriptor) {
    // TODO: Validacion de cercania a la heladera
    suscriptores.add(suscriptor);
  }

  @Override
  public void desuscribir(Suscriptor suscriptor) {
    suscriptores.remove(suscriptor);
  }

  // Este metodo diferencia cada suscripcion
  @Override
  public List<Suscriptor> obtenerInteresados(int cantidadViandas) {
    return suscriptores.stream()
        .filter(s -> s.getCantidadCritica() < cantidadViandas)
        .toList();
  }
}
