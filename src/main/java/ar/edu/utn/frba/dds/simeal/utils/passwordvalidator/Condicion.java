package ar.edu.utn.frba.dds.simeal.utils.passwordvalidator;

public interface Condicion {
  boolean validar(String posiblePassword);
  String mensaje();
}
