package ar.edu.utn.frba.dds.Personas.PersonaVulnerable;

import ar.edu.utn.frba.dds.Personas.Colaborador.Colaborador;
import ar.edu.utn.frba.dds.Personas.Documentacion.Documento;
import ar.edu.utn.frba.dds.Ubicacion.Ubicacion;

import java.time.LocalDate;
import java.util.List;

public class PersonaVulnerable {
  String nombre;
  LocalDate fechaNacimiento;
  LocalDate fechaRegistro;
  Ubicacion domilicio;
  List<PersonaVulnerable> menoresACargo;
  Documento documento;
  Colaborador colaboradorACargo;


}
