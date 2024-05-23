package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
public class Oferta implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private String nombre;
  @Setter
  private Float puntosNecesarios;
  @Setter
  private Rubro rubro;
  @Setter
  private String imagen;

/*
  public Oferta(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/

  @Override
  public double calcularReconocimientoParcial() {
    return 0;
  }

  public void canjear(Colaborador colaborador) {
    try{
      if(!colaborador.puedeCanjear(this))
        throw new RuntimeException("Colaborador no posee suficientes puntos");
    }catch (RuntimeException e){
      return;
    }
    colaborador.gastarPuntos(this.puntosNecesarios);
  }
}
