package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PersonaVulnerable {
  private String nombre;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private Ubicacion domilicio;
  private final List<PersonaVulnerable> hijos;
  private final Documento documento;
  private final Colaborador colaborador;

  public PersonaVulnerable(List<PersonaVulnerable> hijos, LocalDate fechaNacimiento,
                            Documento documento, Colaborador colaborador) {
    this.hijos = hijos;
    this.fechaNacimiento = fechaNacimiento;
    this.documento = documento;
    this.colaborador = colaborador;
  }

  public boolean esMenor() {
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantHijosMenores() {
    return this.hijos.stream().filter(PersonaVulnerable::esMenor).toList().size();
  }
}
