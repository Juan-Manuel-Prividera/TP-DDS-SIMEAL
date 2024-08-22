package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class SugerenciaHeladeras {
  private boolean aceptada;
  private Ubicacion ubicacionDeInteres;
  private List<Heladera> heladerasSugeridas;
  private String sugerencia;

  public SugerenciaHeladeras(Ubicacion ubicacion, List<Heladera> heladeras) {
    this.ubicacionDeInteres = ubicacion;
    this.aceptada = false;
    heladerasSugeridas = new ArrayList<>(heladeras);
  }

  public String getSugerencia(int cercania) {
    sugerencia = "Las heladeras cercanas son: \n";
    for (Heladera heladera : heladerasSugeridas) {
      sugerencia += "\t La heladera ubicada en: " + heladera.getUbicacion().getStringUbi() + "\n";
    }
    return sugerencia;
  }


}
