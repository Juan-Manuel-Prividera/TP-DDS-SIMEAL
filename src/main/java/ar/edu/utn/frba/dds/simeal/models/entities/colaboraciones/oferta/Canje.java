package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
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
  public void realizar() {
    // Concretamente falta esto
    List<AdherirHeladera> colaboracionesDeHeladera = null;

    if (beneficiario.puedeCanjear(this.oferta, CalculadorDeReconocimientos
        .calcularReconocimientoTotal(beneficiario, colaboracionesDeHeladera))) {
      beneficiario.gastarPuntos(oferta.getPuntosNecesarios());
    }
  }
}
