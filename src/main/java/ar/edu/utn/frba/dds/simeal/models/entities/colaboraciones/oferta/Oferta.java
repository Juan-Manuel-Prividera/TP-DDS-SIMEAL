package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
public class Oferta implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private String nombre;
  @Setter
  private double puntosNecesarios;
  @Setter
  private Rubro rubro;
  @Setter
  private String imagen;

  @Override
  public double calcularReconocimientoParcial() {
    return 0;
  }

  // La validacion de si puede canjearla se haria antes de ejecutar este metodo
  public void canjear(Colaborador colaborador) {
    colaborador.gastarPuntos(this.puntosNecesarios);
  }

}
