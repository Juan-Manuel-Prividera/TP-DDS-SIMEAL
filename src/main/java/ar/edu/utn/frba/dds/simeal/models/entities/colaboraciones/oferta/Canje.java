package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class Canje {
  Oferta oferta;
  Colaborador beneficiario;
  LocalDateTime fecha;

  // TODO: Falta el acceso a persistencia para conseguir las colaboraciones de un colaborador especifico
  public void realizar()
  {
    // Concretamente falta esto
    List<ColaboracionPuntuable> colaboraciones = null;

    CalculadorDeReconocimientos calculadorDeReconocimientos = CalculadorDeReconocimientos.getInstance(colaboraciones);

    if (beneficiario.puedeCanjear(this.oferta, calculadorDeReconocimientos.calcularReconocimientoTotal()))
      beneficiario.gastarPuntos(oferta.getPuntosNecesarios());
  }

}
