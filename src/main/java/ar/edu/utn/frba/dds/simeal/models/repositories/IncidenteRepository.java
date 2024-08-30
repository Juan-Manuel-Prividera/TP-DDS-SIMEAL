package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Getter
public class IncidenteRepository implements Repository<Incidente> {
  private final List<Incidente> incidentes;

  public IncidenteRepository() {
    incidentes = new ArrayList<>();
  }

  public void guardar(Incidente incidente) {
    incidentes.add(incidente);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Incidente incidente) {

  }

  @Override
  public List<Incidente> obtenerTodos() {
    return List.of();
  }


}
