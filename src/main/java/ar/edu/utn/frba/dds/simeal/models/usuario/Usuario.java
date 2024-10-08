package ar.edu.utn.frba.dds.simeal.models.usuario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario extends Persistente {
  @Column(name="username", nullable = false)
  private String username;

  @Column(name="hash", nullable = false)
  private String hash;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @ManyToMany
  @JoinTable(
          name = "rol_usuario",
          joinColumns = @JoinColumn( name="usuario_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
  private List<Rol> roles;
}

