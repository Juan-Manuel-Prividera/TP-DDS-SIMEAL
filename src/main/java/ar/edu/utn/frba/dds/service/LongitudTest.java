package ar.edu.utn.frba.dds.service;

public class LongitudTest implements Condicion {
  int minimunLenght;

  @Override
  public boolean validar(String posiblePassword) {
    return posiblePassword.length() >= minimunLenght;
  }
}
