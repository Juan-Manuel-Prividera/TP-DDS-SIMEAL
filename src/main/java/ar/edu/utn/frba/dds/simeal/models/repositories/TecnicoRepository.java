package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TecnicoRepository {
  private List<Tecnico> tecnicos;
  private static TecnicoRepository instance;

  public static TecnicoRepository getInstance() {
    if(instance == null)
      return new TecnicoRepository();
    return instance;
  }
  private TecnicoRepository(){
    tecnicos = new ArrayList<>();
  }
  public void guardar(Tecnico tecnico) {
    tecnicos.add(tecnico);
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
