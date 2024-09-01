package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "oferta")
public class Oferta extends Persistente implements Colaboracion{
  @Getter
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private final Colaborador colaborador;
  @Column
  private final LocalDate fechaDeRealizacion;
  @Column
  private String nombre;
  @Getter
  @Column
  private double puntosNecesarios;
  @Setter
  @ManyToOne
  @JoinColumn(name = "rubro_id", referencedColumnName = "id")
  private Rubro rubro;
  @Setter
  @Column
  private String imagen;


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

  // La validacion de si puede canjearla se haria antes de ejecutar este metodo
  public void canjear(Colaborador colaborador) {
    colaborador.gastarPuntos(this.puntosNecesarios);
  }

}
