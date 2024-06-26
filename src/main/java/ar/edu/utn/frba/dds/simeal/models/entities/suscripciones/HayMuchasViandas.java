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
public class HayMuchasViandas implements Suscripcion {
  @Setter
  private List<Colaborador> suscriptores;
  private Heladera heladera;
  private Mensaje mensaje;
  private int cercaniaNecesaria = 1000;
  public HayMuchasViandas(Heladera heladera) {
    suscriptores = new ArrayList<>();
    mensaje = new Mensaje("Hay muchas viandas en la heladera: "
        + heladera.getNombre());
    this.heladera = heladera;
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
    return suscriptores.stream()
        .filter(s -> s.getCantidadDeViandasAceptable() <= cantidadCritica)
        .toList();
  }

  @Override
  public boolean interesaEsteEvento(TipoEvento tipoEvento) {
    return tipoEvento.equals(TipoEvento.INGRESO);
  }



}
