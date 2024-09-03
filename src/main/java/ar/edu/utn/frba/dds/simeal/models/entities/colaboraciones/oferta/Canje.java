package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "canje")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Canje extends Persistente {
  @ManyToOne
  @JoinColumn(name = "oferta", referencedColumnName = "id")
  Oferta oferta;
  @ManyToOne
  @JoinColumn(name = "beneficiario_id", referencedColumnName = "id")
  Colaborador beneficiario;
  @Column(name = "fecha")
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
