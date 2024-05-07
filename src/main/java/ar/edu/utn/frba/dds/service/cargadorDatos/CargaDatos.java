package ar.edu.utn.frba.dds.service.cargadorDatos;


// Formato
// tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargaDatos {
  private String csvFile;
  private String separador = ",";
  private BufferedReader bufferedReader;
  private String line;
  private List<Colaboracion> listadoColaboraciones;

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
    }

  }
}
