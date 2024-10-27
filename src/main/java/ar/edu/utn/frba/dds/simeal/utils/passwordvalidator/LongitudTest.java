package ar.edu.utn.frba.dds.simeal.utils.passwordvalidator;

public class LongitudTest implements Condicion {
  int minimunLenght;

  public LongitudTest(int minimunLenght) {
    this.minimunLenght = minimunLenght;
  }


  @Override
  public boolean validar(String posiblePassword) {
    return posiblePassword.length() >= minimunLenght;
  }

  @Override
  public String mensaje() {
    // TODO: El navegador no toma la ñ, no sé por qué. Linux...
    return "La contraseña debe tener al menos " + minimunLenght+" caracteres";
  }
}
