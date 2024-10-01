package ar.edu.utn.frba.dds.simeal.models.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaVulnerableDTO {
  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private String numeroDocumento;
  private String fechaNacimiento;
  private List<PersonaVulnerableDTO> hijos;
}
