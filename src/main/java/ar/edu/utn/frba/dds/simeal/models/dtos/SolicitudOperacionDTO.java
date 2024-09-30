package ar.edu.utn.frba.dds.simeal.models.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SolicitudOperacionDTO {
  private String nombre;
  private String ubicacion;
  private String horaFin;
}
