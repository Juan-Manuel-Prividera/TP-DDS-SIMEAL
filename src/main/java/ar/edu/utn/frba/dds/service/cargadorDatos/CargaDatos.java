package ar.edu.utn.frba.dds.service.cargadorDatos;


// Formato
// tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad

import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CargaDatos {
  private String csvFile;
  private String separador = ",";
  private BufferedReader bufferedReader;
  private String line;
  private ArrayList<ColaboracionLectura> listadoColaboraciones;

  public CargaDatos(String csvFile) {
    this.csvFile = csvFile;
    listadoColaboraciones = new ArrayList<>();
    try {
      bufferedReader = new BufferedReader(new FileReader(csvFile));
    } catch (FileNotFoundException e) {
      System.out.printf(e.getMessage());
    }
  }


  // Esto es una prueba para ver si se lee el archivo
  // Falta hacer que machee lo que lee con alguna de nuestras clases
  // O ver si hay que hacer otra clase y guardar ahi
  // TODO
  public void leerColaboradores() throws IOException {
    while((line = bufferedReader.readLine()) != null) {
      String[] camposSeparados = line.split(separador);
      ColaboracionLectura colaboracionLectura = new ColaboracionLectura();
      colaboracionLectura.documento = new DocumentoLectura(camposSeparados[0],camposSeparados[1]);
      colaboracionLectura.nombre = camposSeparados[2];
      colaboracionLectura.apellido = camposSeparados[3];
      colaboracionLectura.mail = camposSeparados[4];
      colaboracionLectura.fechaColaboracion = camposSeparados[5];
      colaboracionLectura.formaColaboracion = camposSeparados[6];
      colaboracionLectura.cantidad = camposSeparados[7];

      colaboracionLectura.mostrarDatos();
    }

  }
}
