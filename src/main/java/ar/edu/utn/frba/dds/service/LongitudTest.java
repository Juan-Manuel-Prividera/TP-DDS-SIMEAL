package ar.edu.utn.frba.dds.service;

public class LongitudTest implements Condicion {
  int minimunLenght;

  public LongitudTest(int minimunLenght){
    this.minimunLenght = minimunLenght;
  }


  @Override
  public boolean validar(String posiblePassword) {
    return posiblePassword.length() >= minimunLenght;
  }
}
