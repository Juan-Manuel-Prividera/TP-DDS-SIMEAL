package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
@Embeddable
public class Documento {
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoDocumento")
  TipoDocumento tipoDocumento;

  @Column(name = "nroDocumento")
  String nroDocumento;

  public Documento(TipoDocumento tipoDocumento, String nroDocumento) {
    this.tipoDocumento = tipoDocumento;
    this.nroDocumento = nroDocumento;
  }

}

