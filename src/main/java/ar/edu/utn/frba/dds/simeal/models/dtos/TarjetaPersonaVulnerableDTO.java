package ar.edu.utn.frba.dds.simeal.models.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaPersonaVulnerableDTO {
  private String numero;
  private String usosDisponibles;
  private String nombrePropietario;
  private String apellidoPropietario;
  private String dniPropietario;
  private String edadPropietario;
}
