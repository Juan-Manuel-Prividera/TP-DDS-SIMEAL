package ar.edu.utn.frba.dds.simeal.service.creacionales;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;

import java.time.LocalDate;


public class ColaboracionBuilder {

  public static ColaboracionPuntuable crearColaboracionPuntuable(
      TipoColaboracion tipoColaboracionPuntuable,
      LocalDate fecha, Colaborador colaborador, int cantidad) {

    return switch (tipoColaboracionPuntuable) {
      case DINERO -> DonarDinero.create(colaborador, fecha, cantidad);

      case DONACION_VIANDA -> DonarVianda.create(colaborador, fecha);

      case REDISTRIBUCION_VIANDA -> DistribuirVianda.create(colaborador, fecha, cantidad);

      case ENTREGA_TARJETA -> DarDeAltaPersonaVulnerable.create(colaborador, fecha);

      case ADHERIR_HELADERA -> AdherirHeladera.create(colaborador, fecha);

      default -> throw new IllegalStateException("Unexpected value: " + tipoColaboracionPuntuable);
    };
  }

  public static Colaboracion crearColaboracion(TipoColaboracion tipoColaboracion, LocalDate fecha,
                                               Colaborador colaborador, int cantidad) {
    if (TipoColaboracion.OFERTA.equals(tipoColaboracion)) {
      return Oferta.create(colaborador, fecha, cantidad);
    } else {
      return crearColaboracionPuntuable(tipoColaboracion, fecha, colaborador, cantidad);
    }
  }
}
