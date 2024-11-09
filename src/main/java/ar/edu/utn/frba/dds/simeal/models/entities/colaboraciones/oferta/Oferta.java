package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "oferta")
public class Oferta extends Persistente {
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private final Colaborador colaborador;

  @Column (name = "fechaDeRealizacion")
  private final LocalDate fechaDeRealizacion;

  @Column (name = "nombre")
  private String nombre;

  @Column (name = "puntosNecesario")
  private double puntosNecesarios;

  @ManyToOne
  @JoinColumn(name = "rubro_id", referencedColumnName = "id")
  private Rubro rubro;

  @Column (name = "imagen")
  private String imagen;

  @ManyToOne
  @JoinColumn(name="producto_id", referencedColumnName = "id")
  private Producto producto;


  public static Oferta create(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    return Oferta.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();
  }

  public static Oferta create(Colaborador colaborador, LocalDate fechaDeRealizacion,
                              double puntosNecesarios) {
    return Oferta.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .puntosNecesarios(puntosNecesarios)
        .build();
  }

  //Usado en OfertasController
  public static Oferta create(Colaborador colaborador, String nombre, LocalDate fechaDeRealizacion, double puntosNecesarios, Rubro rubro, String imagen, Producto producto) {
    return Oferta.builder()
            .colaborador(colaborador)
            .nombre(nombre)
            .fechaDeRealizacion(fechaDeRealizacion)
            .puntosNecesarios(puntosNecesarios)
            .rubro(rubro)
            .imagen(imagen)
            .producto(producto)
            .build();
  }
  // La validacion de si puede canjearla se haria antes de ejecutar este metodo
  public void canjear(Colaborador colaborador) {
    colaborador.gastarPuntos(this.puntosNecesarios);
  }

}
