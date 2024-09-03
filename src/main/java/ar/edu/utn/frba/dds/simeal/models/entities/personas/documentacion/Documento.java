package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Documento {
  @Column(name = "tipoDocumento")
  TipoDocumento tipoDocumento;
  @Column(name = "tipoDocumento")
  String nroDocumento;

  public Documento(TipoDocumento tipoDocumento, String nroDocumento) {
    this.tipoDocumento = tipoDocumento;
    this.nroDocumento = nroDocumento;
  }

}

