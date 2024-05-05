package ar.edu.utn.frba.dds.service.cargadorDatos;

import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;

public class DocumentoLectura {
  String nroDocumento;
  String tipoDocumento;

  public DocumentoLectura(String tipoDocumento, String nroDocumento) {
    this.nroDocumento = nroDocumento;
    this.tipoDocumento = tipoDocumento;
  }
}
