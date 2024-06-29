package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;

import java.util.List;

public interface Suscripcion {
  void suscribir(Colaborador suscriptor);
  void desuscribir(Colaborador suscriptor);
  List<Colaborador> obtenerInteresados(int cantidadCritica);


  Heladera getHeladera();
  Mensaje getMensaje();
  void setSuscriptores(List<Colaborador> suscriptores);

  boolean interesaEsteEvento(TipoEvento tipoEvento);
}
