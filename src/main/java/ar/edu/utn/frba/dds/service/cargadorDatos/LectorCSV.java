package ar.edu.utn.frba.dds.service.cargadorDatos;


// Formato
// tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.domain.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.Email;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectorCSV {
  private String csvFile;
  private String separador = ",";
  private String[] line;

  private ColaboracionFactory colaboracionFactory;


  public LectorCSV(String csvFile) throws FileNotFoundException {
    this.csvFile = csvFile;
  }

  // tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad
  public List<Colaboracion> leerColaboradores() throws IOException, CsvException {
    List<Colaboracion> listadoColaboraciones = new ArrayList<>();

    CSVReader lector = new CSVReaderBuilder(new FileReader(csvFile))
            .withCSVParser(new CSVParserBuilder()
                    .withSeparator(',')
                    .withQuoteChar('"')
                    .build()).build();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    while ((line = lector.readNext())!= null){
      TipoDocumento tipoDocumento = TipoDocumento.valueOf(line[0]);
      String numeroDocumento = line[1];
      String nombre = line[2];
      String apellido = line[3];
      String mail = line[4];
      LocalDate fechaColaboracion = LocalDate.parse(line[5], formatter);
      TipoColaboracion tipoColaboracion = TipoColaboracion.valueOf(line[6]);
      int cantidad = Integer.parseInt(line[7]);


      Documento documento = new Documento(tipoDocumento,numeroDocumento);
      Email email = new Email(mail);
      Colaborador colaborador = new Colaborador(documento,nombre,apellido);
      colaborador.addMedioContacto(email);
      colaborador.setDocumento(documento);

      colaboracionFactory = new ColaboracionFactory();
      Colaboracion colaboracion = colaboracionFactory.crearColaboracion(tipoColaboracion,fechaColaboracion,colaborador,cantidad);

      listadoColaboraciones.add(colaboracion);
    }
    return listadoColaboraciones;
  }
}
