package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "persona_pulnerable")
@NoArgsConstructor(force = true)
public class PersonaVulnerable extends Persistente {
  @Column (name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column (name = "fechaNacimiento")
  private LocalDate fechaNacimiento;

  @Column(name = "edad")
  private int edad;

  @Column (name = "fechaRegistro")
  private LocalDate fechaRegistro;

  @OneToOne @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @JoinColumn(name="ubicacion_id", referencedColumnName = "id")
  private Ubicacion domilicio;

  @OneToMany @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "padre_id", referencedColumnName = "id")
  private List<PersonaVulnerable> hijos = new ArrayList<>();

  @Column(name = "cant_hijos")
  private int cantidadHijosMenores;

  @Embedded @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private Documento documento;

  public PersonaVulnerable(LocalDate of) {
    this.fechaNacimiento = of;
  }


  public boolean esMenor() {
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantHijosMenores() {
    if (hijos == null) {
      return 0;
    }
    return this.hijos.stream().filter(PersonaVulnerable::esMenor).toList().size();
  }

  public void calcularEdad() {
    edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
  }
}
