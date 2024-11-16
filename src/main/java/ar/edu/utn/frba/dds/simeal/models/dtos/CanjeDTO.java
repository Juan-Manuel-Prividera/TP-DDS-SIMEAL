package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Canje;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
public class CanjeDTO {
  private String ofertaNombre;
  private String colaboradorNombre;
  private LocalDateTime fecha;
  private Double coste;

  public CanjeDTO(Canje canje) {
    this.ofertaNombre = canje.getOferta().getNombre();
    this.colaboradorNombre = canje.getBeneficiario().getNombre();
    this.fecha = canje.getFecha();
    this.coste = canje.getCoste();
  }
}
