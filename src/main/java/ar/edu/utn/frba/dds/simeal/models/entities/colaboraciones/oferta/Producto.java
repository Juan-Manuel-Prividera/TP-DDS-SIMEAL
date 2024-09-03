package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;

@AllArgsConstructor
public class Producto extends Persistente {
  @Column
  private String nombre;
  @Column
  private String descripcion;
  @Embedded
  private Categoria categoria;
}
