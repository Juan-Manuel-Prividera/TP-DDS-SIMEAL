package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HuboUnDesperfecto implements Suscripcion {
  @Setter
  private List<Colaborador> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;
  private int cercaniaNecesaria = 1000;
  private SugerenciaHeladeras sugerenciaHeladeras;


  public HuboUnDesperfecto(Heladera heladera) {
    sugerenciaHeladeras = new SugerenciaHeladeras(heladera.getUbicacion());
    suscriptores = new ArrayList<>();
    this.heladera = heladera;
    this.mensaje = new Mensaje("Hubo un desperfecto en la heladera: "
        + heladera.getNombre() + "\n" + sugerenciaHeladeras.getSugerencia(cercaniaNecesaria));
  }

  @Override
  public void suscribir(Colaborador suscriptor) {
    if (suscriptor.getUbicacion().estaCercaDe(heladera.getUbicacion(),cercaniaNecesaria)) {
      suscriptores.add(suscriptor);
    } else {
      System.out.println("No esta lo suficientemente cerca para suscribirse a esta heladera");

    }
  }

  @Override
  public void desuscribir(Colaborador suscriptor) {
    suscriptores.remove(suscriptor);
  }

  @Override
  public List<Colaborador> obtenerInteresados(int cantidadCritica) {
    return suscriptores;
  }

  @Override
  public boolean interesaEsteEvento(TipoEvento tipoEvento) {
    return tipoEvento.equals(TipoEvento.INCIDENTE);
  }
}
