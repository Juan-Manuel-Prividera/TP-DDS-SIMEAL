package ar.edu.utn.frba.dds.simeal.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorDeInputs {
  public static Boolean soloNumero(String str) {
    return str.matches("\\d+");
  }
  public static Boolean soloLetras(String str) {
    return str.matches("[a-zA-Z]+");
  }

  public static Boolean esFechaValida(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
      LocalDate.parse(fecha, formatter);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
