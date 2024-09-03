package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Column
  private String nombre;
  @Column
  private String descripcion;
  @Embedded
  private Categoria categoria;
}
