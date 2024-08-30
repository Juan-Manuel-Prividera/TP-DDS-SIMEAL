package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class VisitaTecnicaRepository implements Repository<VisitaTecnica> {
  private List<VisitaTecnica> visitas;

  public VisitaTecnicaRepository() {
    visitas = new ArrayList<>();
  }

  public void guardar(VisitaTecnica visita) {
    visitas.add(visita);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(VisitaTecnica visitaTecnica) {

  }

  @Override
  public List<VisitaTecnica> obtenerTodos() {
    return List.of();
  }

  public void eliminar(VisitaTecnica visita) {
    visitas.remove(visita);
  }


}
