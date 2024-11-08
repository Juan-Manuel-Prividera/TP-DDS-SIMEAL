package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "canje")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class Canje extends Persistente {
  @ManyToOne
  @JoinColumn(name = "oferta_id", referencedColumnName = "id")
  private Oferta oferta;

  @ManyToOne
  @JoinColumn(name = "beneficiario_id", referencedColumnName = "id")
  private Colaborador beneficiario;

  @Column(name = "fecha")
  private LocalDateTime fecha;

  @Column(name="coste")
  private Double coste;

  // Esto se va a pasar a controller proximanete :)
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
