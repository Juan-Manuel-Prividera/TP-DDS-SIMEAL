package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;

import java.util.ArrayList;
import java.util.List;

public class SugerenciaHeladeras {
  private HeladeraRepository heladeraRepository;
  private boolean aceptada;
  private Ubicacion ubicacionDeInteres;
  private List<Heladera> heladerasSugeridas;
  private String sugerencia;

  public SugerenciaHeladeras(Ubicacion ubicacion) {
    this.ubicacionDeInteres = ubicacion;
    this.aceptada = false;
  }

  public String getSugerencia(int cercania) {
    sugerencia = "Las heladeras cercanas son: \n";
    heladerasSugeridas = obtenerHeladerasCercanas(cercania);
    for (Heladera heladera : heladerasSugeridas) {
      sugerencia += "\t La heladera ubicada en: " + heladera.getUbicacion().getStringUbi() + "\n";
    }
    return sugerencia;

  }
  private List<Heladera> obtenerHeladerasCercanas(int cercania) {
    heladeraRepository = HeladeraRepository.getInstance();
    return new ArrayList<>(heladeraRepository.buscarPorCercania(ubicacionDeInteres,cercania));
  }

}
