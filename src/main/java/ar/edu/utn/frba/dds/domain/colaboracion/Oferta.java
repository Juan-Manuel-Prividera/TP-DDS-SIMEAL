package ar.edu.utn.frba.dds.domain.colaboracion;

import ar.edu.utn.frba.dds.domain.Rubro;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
  public void colaborar() {

  }

  @Override
  public Float calcularReconocimientoParcial() {
    return 0F;
  }

  public void canjear() {
    //TODO
  }
}
