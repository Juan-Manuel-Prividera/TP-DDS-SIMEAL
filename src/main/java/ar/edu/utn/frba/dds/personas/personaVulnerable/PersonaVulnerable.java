package ar.edu.utn.frba.dds.personas.personaVulnerable;

import ar.edu.utn.frba.dds.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.ubicacion.Ubicacion;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class PersonaVulnerable {
  private final String nombre;
  private final LocalDate fechaNacimiento;
  private final LocalDate fechaRegistro;
  private Ubicacion domilicio;
  private List<PersonaVulnerable> menoresACargo;
  private final Documento documento;
  private final Colaborador colaboradorACargo;

}
