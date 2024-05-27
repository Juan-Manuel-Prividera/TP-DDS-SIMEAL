package ar.edu.utn.frba.dds.simeal.service;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;


public class ColaboracionBuilder {

  public Colaboracion crearColaboracion(TipoColaboracion tipoColaboracion, LocalDate fecha,
                                        Colaborador colaborador, int cantidad) {
    return switch (tipoColaboracion) {
      case DINERO -> DonarDinero.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha).cantidadDinero(cantidad)
          .build();

      case DONACION_VIANDA -> DonarVianda.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha)
          .build();

      case REDISTRIBUCION_VIANDA -> DistribuirVianda.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha).cantidadViandasMover(cantidad)
          .build();

      case ENTREGA_TARJETA -> DarDeAltaPersonaVulnerable.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha)
          .build();

      case OFERTA -> Oferta.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha)
          .build();

      case ADHERIR_HELADERA -> AdherirHeladera.builder()
          .colaborador(colaborador).fechaDeRealizacion(fecha)
          .build();
    };
  }
}
