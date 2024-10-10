package ar.edu.utn.frba.dds.simeal.utils;

import org.apache.commons.math3.random.RandomDataGenerator;

public class GeneradorNrosTarjeta {
  public static String generarCodigo() {
    RandomDataGenerator generator = new RandomDataGenerator();
    long nroRandom = generator.nextLong(100_000_000L, 999_999_999L);
    return Long.toString(nroRandom);
  }
}
