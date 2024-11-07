package ar.edu.utn.frba.dds.simeal.utils.cargadordatos;

import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.ValidadorDeInputs;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LectorCsv {
  private double cantTotalColaboraciones = 0;
  private double cantLeidas = 0;
  // tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad
  public List<ColaboracionPuntuable> leerColaboradores(String csvFile) throws IOException, CsvException {
    List<ColaboracionPuntuable> listadoColaboracionesPuntuable = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    CSVReader lector = new CSVReaderBuilder(
        new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
            .build();

    List<String[]> filas = lector.readAll();
    cantTotalColaboraciones = filas.size();

    for (String[] line : filas) {
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
      TipoColaboracion tipoColaboracion = TipoColaboracion.valueOf(line[6]);
      int cantidad = Integer.parseInt(line[7]);
      System.out.println(nombre);

      Colaborador colaborador =
          validarSiExisteElColaboradorEnLista(numeroDocumento, listadoColaboracionesPuntuable);

      if (colaborador == null) {
        Documento documento = new Documento(tipoDocumento, numeroDocumento);
        Contacto contacto = new Contacto(mail, MedioDeContactoFactory.crearMedioDeContacto(mail));
        colaborador = new Colaborador(documento, nombre, apellido);
        colaborador.setContactoPreferido(contacto);
      }
      ColaboracionPuntuable colaboracionPuntuable = ColaboracionBuilder
          .crearColaboracion(tipoColaboracion, fechaColaboracion, colaborador, cantidad);

      listadoColaboracionesPuntuable.add(colaboracionPuntuable);
      cantLeidas ++;

    }
    // Esta comentado para no mandar un mail cada vez que se ejecuta :)
    //enviarCredencialesDeAcceso(listadoColaboracionesPuntuable.stream().map(ColaboracionPuntuable::getColaborador).toList());
    return listadoColaboracionesPuntuable;
  }

  private Colaborador validarSiExisteElColaboradorEnLista(String numeroDocumento,
                                               List<ColaboracionPuntuable> colaboraciones) {
    for (ColaboracionPuntuable colaboracion : colaboraciones) {
      String nro = colaboracion.getColaborador().getDocumento().getNroDocumento();
      if (numeroDocumento.equals(nro)) {
        Logger.debug("Colab Repetido");
        return colaboracion.getColaborador();
      }
    }
    return null;
  }

  private void enviarCredencialesDeAcceso(List<Colaborador> colaboradores) {
    for (Colaborador colaborador : colaboradores) {
      colaborador.setUsuario(new Usuario(colaborador.getNombre(),"1234", null));
      Notificador.notificar(colaborador,new Mensaje(("Tus credenciales de acceso son: tu nombre y la contraseña 1234")));
    }
  }

  public Boolean validarFormato(File csvFile) throws IOException, CsvException {
    CSVReader lector = new CSVReaderBuilder(
      new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))
      .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
      .build();
    List<String[]> filas;
    try {
      filas = lector.readAll();
    } catch (Exception e) {
      return false;
    }
    for (String[] fila : filas) {
      for (int i = 0; i < fila.length; i++) {
        fila[i] = fila[i].strip();
      }

      if(fila.length != 8){
        Logger.error("La cantidad de columnas en una fila del csv no es 8 es: " + fila.length);
        return false;
      }
      try {
        TipoDocumento.valueOf(fila[0]);
      } catch (IllegalArgumentException e) {
        Logger.error("El tipo de documento no es valido: " + fila[0]);
        return false;
      }
      if(!ValidadorDeInputs.soloNumero(fila[1])) {
        Logger.error("El dni no es valido: " + fila[1]);
        return false;
      }
      if(!ValidadorDeInputs.soloLetras(fila[2]) || !ValidadorDeInputs.soloLetras(fila[3])) {
        Logger.error("El nombre y/o apellido no son validos");
        return false;
      }
      if(!fila[4].contains("@")) {
        Logger.error("El email no es valido: " + fila[4]);
        return false;
      }
      if(!ValidadorDeInputs.esFechaValida(fila[5])) {
        Logger.error("No es una fecha valida: "+ fila[5]);
        return false;
      }
      try {
        TipoColaboracion.valueOf(fila[6]);
      } catch (IllegalArgumentException e) {
        Logger.error("No es un tipo de colaboracion valido: " + fila[6]);
        return false;
      }
      if (!ValidadorDeInputs.soloNumero(fila[7])) {
        Logger.error("No es una cantidad valida: " + fila[7]);
        return false;
      }
    }
    return true;
  }


}
