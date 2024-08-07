package ar.edu.utn.frba.dds.simeal.models.entities.operacionHeladera;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperacionHeladera {
  private SolicitudOperacionHeladera solicitud;
  private LocalDateTime horaDeRealizacion;
}
