package ar.edu.utn.frba.dds.simeal.utils;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Localidad;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.security.Provider;
import java.util.List;

public class CalculadorHeladerasCercanas {
  private static final int distanciaCercana = 5; // km

  public static void setHeladerasCercanas(Heladera heladera) {
    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);

    for (Heladera h : heladeras) {
      if (!h.getId().equals(heladera.getId()) && heladera.getUbicacion().estaCercaDe(h.getUbicacion(),distanciaCercana))
        heladera.addHeladeraCercana(h);
      Logger.debug("Heladera :" + h.getId() +" es cercana");
    }
  }

}
