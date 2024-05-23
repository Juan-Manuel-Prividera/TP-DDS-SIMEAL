package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class PersonaVulnerable {
  private final String nombre;
  private final LocalDate fechaNacimiento;
  private final LocalDate fechaRegistro;
  private Ubicacion domilicio;
  private List<PersonaVulnerable> hijos;
  private final Documento documento;
  private final Colaborador colaboradorACargo;

  public boolean esMenor(){
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantMenoresACargo(){
    return this.hijos.stream().filter(h-> h.esMenor()).toList().size();
  }
}
