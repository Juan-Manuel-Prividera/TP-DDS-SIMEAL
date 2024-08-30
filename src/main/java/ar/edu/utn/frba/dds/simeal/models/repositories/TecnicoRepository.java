package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TecnicoRepository implements Repository<Tecnico> {
  private List<Tecnico> tecnicos;

  public TecnicoRepository(){
    tecnicos = new ArrayList<>();
  }

  public void guardar(Tecnico tecnico) {
    tecnicos.add(tecnico);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Tecnico tecnico) {

  }

  @Override
  public List<Tecnico> obtenerTodos() {
    return List.of();
  }

  public void eliminar(Tecnico tecnico) {
    tecnicos.add(tecnico);
  }

  public List<Tecnico> buscarPor(Ubicacion ubicacion) {
    return tecnicos.stream().filter(t -> t.getAreaDeCobertura().cubreEstaUbicacion(ubicacion)).toList();
  }

  public Tecnico buscarMasCercanoA(Ubicacion ubicacion){
    return buscarPor(ubicacion).stream().min((t1,t2) -> {
      double dist1 = t1.getAreaDeCobertura().getUbicacion().distanciaA(ubicacion);
      double dist2 = t2.getAreaDeCobertura().getUbicacion().distanciaA(ubicacion);
      return Double.compare(dist1,dist2);
    }).orElse(null);
  }


}
