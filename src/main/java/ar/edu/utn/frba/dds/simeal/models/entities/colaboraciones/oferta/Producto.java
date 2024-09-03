package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
public class Producto extends Persistente {
  @Column
  private String nombre;
  @Column
  private String descripcion;
  @Embedded
  private Categoria categoria;
}
