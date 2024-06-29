package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class DistribucionDeViandasRepository {
  private List<DistribuirVianda> distribuciones;
  private static DistribucionDeViandasRepository instance;

  public static DistribucionDeViandasRepository getInstance() {
    if (instance == null) {
      return new DistribucionDeViandasRepository();
    }
    return instance;
  }

  public DistribucionDeViandasRepository() {
    distribuciones = new ArrayList<>();
  }

  public void guardar(DistribuirVianda distribucion) {
    distribuciones.add(distribucion);
  }


}

