package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@AllArgsConstructor
public class Oferta implements Colaboracion {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private String nombre;
  @Getter
  private double puntosNecesarios;
  @Setter
  private Rubro rubro;
  @Setter
  private String imagen;

  private Producto[] productos;

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
