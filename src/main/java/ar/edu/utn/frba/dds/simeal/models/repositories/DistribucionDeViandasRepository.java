package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

// Ver de generar un repositorio de colaboraciones en general y
// busque a partir de un método  las específicas requeridas

@Getter
public class DistribucionDeViandasRepository implements Repository<DistribuirVianda> {
  private List<DistribuirVianda> distribuciones;

  public DistribucionDeViandasRepository() {
    distribuciones = new ArrayList<>();
  }

  @Override
  public void guardar(DistribuirVianda distribucion) {
    distribuciones.add(distribucion);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(DistribuirVianda distribuirVianda) {

  }

  @Override
  public List<DistribuirVianda> obtenerTodos() {
    return List.of();
  }


}

