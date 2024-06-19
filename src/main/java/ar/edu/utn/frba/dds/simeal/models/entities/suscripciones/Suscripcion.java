package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.List;

public interface Suscripcion {
  void suscribir(Suscriptor suscriptor);
  void desuscribir(Suscriptor suscriptor);
  List<Suscriptor> obtenerInteresados(int cantidadCritica);
  boolean esEsteTipo(String tipo);


  Heladera getHeladera();
  Mensaje getMensaje();
  void setSuscriptores(List<Suscriptor> suscriptores);

}
