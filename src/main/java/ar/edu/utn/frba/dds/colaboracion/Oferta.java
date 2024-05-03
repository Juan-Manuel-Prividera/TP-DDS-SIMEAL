package ar.edu.utn.frba.dds.colaboracion;

import ar.edu.utn.frba.dds.Rubro;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Oferta implements Colaboracion {
  private final String nombre;
  //no es final para dar lugar a posibles modificaciones en el coste
  @Setter Float puntosNecesarios;
  @Setter Rubro rubro;
  @Setter String imagen;

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
