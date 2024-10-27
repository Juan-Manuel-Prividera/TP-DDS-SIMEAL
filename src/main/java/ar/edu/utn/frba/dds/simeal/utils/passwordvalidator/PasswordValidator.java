package ar.edu.utn.frba.dds.simeal.utils.passwordvalidator;

import java.util.ArrayList;


public class PasswordValidator {
  ArrayList<Condicion> condiciones;

  public PasswordValidator(ArrayList<Condicion> condiciones) {
    this.condiciones = new ArrayList<>();
    this.condiciones.addAll(condiciones);
  }

  // Returns the error message
  public String validate(String posiblePassword) {
    for (Condicion condicion : condiciones) {
      if (!condicion.validar(posiblePassword)) {
        return condicion.mensaje();
      }
    }
    return null;
  }

}
