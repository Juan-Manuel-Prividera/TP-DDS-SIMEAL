package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.List;

public interface Suscripcion {
  void suscribir(Suscriptor suscriptor);
  void desuscribir(Suscriptor suscriptor);
  List<Suscriptor> obtenerInteresados(int cantidadViandas);
  Heladera getHeladera();
  Mensaje getMensaje();
}
