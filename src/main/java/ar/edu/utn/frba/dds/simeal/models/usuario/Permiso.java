package ar.edu.utn.frba.dds.simeal.models.usuario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="permisos")
public class Permiso extends Persistente {
  @Column(name="descripcion")
  private String descripcion;
}
