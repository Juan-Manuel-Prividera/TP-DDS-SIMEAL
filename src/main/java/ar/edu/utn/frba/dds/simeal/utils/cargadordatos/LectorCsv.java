package ar.edu.utn.frba.dds.simeal.utils.cargadordatos;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.ColaboradorController;
import ar.edu.utn.frba.dds.simeal.controllers.UsuariosController;
import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LectorCsv {
  private double cantTotalColaboraciones = 0;
  private double cantLeidas = 0;
  private List<Colaborador> colaboradoresActualesEnSistema = new ArrayList<>();
  // tipoDoc, NroDoc, Nombre, Apellido, Mail, FechaColab, FormaColab, Cantidad
  public List<ColaboracionPuntuable> leerColaboradores(String csvFile) throws IOException, CsvException {
    colaboradoresActualesEnSistema = (List<Colaborador>) ServiceLocator.getRepository(ColaboracionRepository.class)
      .obtenerTodos(Colaborador.class);
    List<ColaboracionPuntuable> listadoColaboracionesPuntuable = new ArrayList<>();
    List<Colaborador> colaboradores = new ArrayList<>();
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

      Colaborador colaborador =
          validarSiExisteElColaboradorEnLista(nombre, numeroDocumento, colaboradores);

      if (colaborador == null) {
        Documento documento = new Documento(tipoDocumento, numeroDocumento);
        Contacto contacto = new Contacto(mail, MedioDeContactoFactory.crearMedioDeContactoDeString("email"));
        colaborador = new Colaborador(documento, nombre, apellido);
        colaborador.setContactoPreferido(contacto);
        colaborador.addContacto(contacto);
        colaboradores.add(colaborador);
        Logger.debug("Agrego nuevo colab a la lista de colaboradores");
      }
      ColaboracionPuntuable colaboracionPuntuable = ColaboracionBuilder
          .crearColaboracion(tipoColaboracion, fechaColaboracion, colaborador, cantidad);

      listadoColaboracionesPuntuable.add(colaboracionPuntuable);
      cantLeidas ++;

    }

    enviarCredencialesDeAcceso(colaboradores);
    return listadoColaboracionesPuntuable;
  }

  private Colaborador validarSiExisteElColaboradorEnLista(String nombre, String numeroDocumento, List<Colaborador> colaboradores) {
    // TODO: Chequear esto por las dudas...
    if (!colaboradoresActualesEnSistema.isEmpty()) {
      for (Colaborador colab : colaboradoresActualesEnSistema) {
        String nro = colab.getDocumento().getNroDocumento();
        String nombreC = colab.getNombre();
        if (numeroDocumento.equals(nro) && nombre.equals(nombreC)) {
          Logger.debug("Colab Repetido");
          return colab;
        }
      }
    }
    for (Colaborador colab : colaboradores) {
      String nro = colab.getDocumento().getNroDocumento();
      String nombreC = colab.getNombre();
      if (numeroDocumento.equals(nro) && nombre.equals(nombreC)) {
        Logger.debug("Colab Repetido");
        return colab;
      }
    }
    Logger.debug("No se repitio colab");
    return null;
  }

  private void enviarCredencialesDeAcceso(List<Colaborador> colaboradores) {
    for (Colaborador colaborador : colaboradores) {
      ServiceLocator.getController(UsuariosController.class).crearUsuarioDeMigracion(colaborador);
      TarjetaColaborador tarjetaColaborador = new TarjetaColaborador(colaborador,LocalDate.now());
      ServiceLocator.getRepository(TarjetaColaboradorRepository.class).guardar(tarjetaColaborador);
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
