package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;


@Getter
@AllArgsConstructor
@Entity
@Table(name = "personaVulnerable")
public class PersonaVulnerable extends Persistente {
  @Column
  private String nombre;
  @Column
  private LocalDate fechaNacimiento;
  @Column
  private LocalDate fechaRegistro;
  @Embedded
  private Ubicacion domilicio;
  private final List<PersonaVulnerable> hijos;
  @Embedded
  private final Documento documento;

  public PersonaVulnerable() { }

  public boolean esMenor() {
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantHijosMenores() {
    return this.hijos.stream().filter(PersonaVulnerable::esMenor).toList().size();
  }
}
