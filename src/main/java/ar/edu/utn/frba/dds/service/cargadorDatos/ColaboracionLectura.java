package ar.edu.utn.frba.dds.service.cargadorDatos;

import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ColaboracionLectura {
  DocumentoLectura documento;
  String nombre;
  String apellido;
  String mail;
  String  fechaColaboracion; // Tiene que ser local date TODO
  String formaColaboracion; // Deberia ser otra cosa TODO
  String cantidad;


  public void mostrarDatos(){
    System.out.println( documento.tipoDocumento + documento.nroDocumento + nombre + apellido + mail + fechaColaboracion + formaColaboracion + cantidad);
  }


}
