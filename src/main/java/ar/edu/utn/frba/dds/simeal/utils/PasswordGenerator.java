package ar.edu.utn.frba.dds.simeal.utils;

import java.security.SecureRandom;

public class PasswordGenerator {
  private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMEROS = "0123456789";
  private static final String CARACTERES_ESPECIALES = "!@#$%^&*()-_=+<>?";

  private static final String TODOS_CARACTERES = MAYUSCULAS + MINUSCULAS + NUMEROS + CARACTERES_ESPECIALES;
  private static final SecureRandom random = new SecureRandom();

  public static String generar(int longitud) {
    if (longitud < 8) {
      throw new IllegalArgumentException("La longitud debe ser al menos de 8 caracteres para mayor seguridad.");
    }

    StringBuilder password = new StringBuilder(longitud);

    // Asegurar que la password tenga al menos un carácter de cada tipo
    password.append(MAYUSCULAS.charAt(random.nextInt(MAYUSCULAS.length())));
    password.append(MINUSCULAS.charAt(random.nextInt(MINUSCULAS.length())));
    password.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
    password.append(CARACTERES_ESPECIALES.charAt(random.nextInt(CARACTERES_ESPECIALES.length())));

    // Completar la password con caracteres aleatorios de todos los tipos
    for (int i = 4; i < longitud; i++) {
      password.append(TODOS_CARACTERES.charAt(random.nextInt(TODOS_CARACTERES.length())));
    }

    // Mezclar los caracteres para hacerla menos predecible
    return mezclarCaracteres(password.toString());
  }

  private static String mezclarCaracteres(String input) {
    char[] caracteres = input.toCharArray();
    for (int i = 0; i < caracteres.length; i++) {
      int j = random.nextInt(caracteres.length);
      char temp = caracteres[i];
      caracteres[i] = caracteres[j];
      caracteres[j] = temp;
    }
    return new String(caracteres);
  }
}