package ar.edu.utn.frba.dds.simeal.service.cargadordatos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LectorCsv {
  private final String csvFile;


  public LectorCsv(String csvFile) {
    this.csvFile = csvFile;
  }

  // tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad
  public List<ColaboracionPuntuable> leerColaboradores() throws IOException, CsvException {
    List<ColaboracionPuntuable> listadoColaboraciones = new ArrayList<>();
    String[] line;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ColaboracionBuilder colaboracionBuilder = new ColaboracionBuilder();

    CSVReader lector = new CSVReaderBuilder(
        new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
            .build();

    while ((line = lector.readNext()) != null) {
      if (Objects.equals(line[0], "\n")) {
        continue;
      }

      for (int i = 0; i < line.length; i++) {
        line[i] = line[i].strip();
      }

      TipoDocumento tipoDocumento = TipoDocumento.valueOf(line[0]);
      String numeroDocumento = line[1];
      String nombre = line[2];
      String apellido = line[3];
      String mail = line[4];
      LocalDate fechaColaboracion = LocalDate.parse(line[5], formatter);
      TipoColaboracionPuntuable tipoColaboracionPuntuable = TipoColaboracionPuntuable.valueOf(line[6]);
      int cantidad = Integer.parseInt(line[7]);


      Documento documento = new Documento(tipoDocumento, numeroDocumento);
      Email email = new Email(mail, null);
      Colaborador colaborador = new Colaborador(documento, nombre, apellido);
      colaborador.addMedioContacto(email);

      ColaboracionPuntuable colaboracionPuntuable = colaboracionBuilder
          .crearColaboracion(tipoColaboracionPuntuable, fechaColaboracion, colaborador, cantidad);

      listadoColaboraciones.add(colaboracionPuntuable);


    }
    return listadoColaboraciones;
  }
}
