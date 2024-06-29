package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperacionHeladera {
  private SolicitudOperacionHeladera solicitud;
  private LocalDateTime horaDeRealizacion;
}
