package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;

import java.util.List;

public interface Notificacion {
  List<Colaborador> obtenerInteresados(List<Suscripcion> suscripciones, int cantidadViandas);

  Mensaje getMensaje();
}
