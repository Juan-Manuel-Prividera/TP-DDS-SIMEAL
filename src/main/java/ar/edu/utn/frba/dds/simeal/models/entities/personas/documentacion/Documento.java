package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;



public class Documento {
  TipoDocumento tipoDocumento;
  String nroDocumento;

  public Documento(TipoDocumento tipoDocumento, String nroDocumento){
    this.tipoDocumento = tipoDocumento;
    this.nroDocumento = nroDocumento;
  }

}

