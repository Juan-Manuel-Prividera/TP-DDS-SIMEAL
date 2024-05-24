package ar.edu.utn.frba.dds.simeal.service;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirVianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donarDinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;

import java.time.LocalDate;
import java.util.ArrayList;

public class ColaboracionBuilder {


  public Colaboracion crearColaboracion(TipoColaboracion tipoColaboracion, LocalDate fecha, Colaborador colaborador, int cantidad) {
      return switch (tipoColaboracion) {
        case DINERO -> DonarDinero.builder().cantidadDinero(cantidad).colaborador(colaborador).fechaDeRealizacion(fecha).build();
        case DONACION_VIANDA -> DonarVianda.builder().colaborador(colaborador).fechaDeRealizacion(fecha).build();
        case REDISTRIBUCION_VIANDA-> DistribuirVianda.builder().colaborador(colaborador).fechaDeRealizacion(fecha).cantidadViandasAMover(cantidad).build();
        case ENTREGA_TARJETA -> DarDeAltaPersonaVulnerable.builder().colaborador(colaborador).fechaDeRealizacion(fecha).build();
        case OFERTA -> Oferta.builder().colaborador(colaborador).fechaDeRealizacion(fecha).build();
        case ADHERIR_HELADERA -> AdherirHeladera.builder().colaborador(colaborador).fechaDeRealizacion(fecha).build();
      };

  }
}
