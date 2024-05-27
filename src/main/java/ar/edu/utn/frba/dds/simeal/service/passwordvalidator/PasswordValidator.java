package ar.edu.utn.frba.dds.simeal.service.passwordvalidator;

import java.util.ArrayList;


public class PasswordValidator {
  ArrayList<Condicion> condiciones;


  public PasswordValidator(ArrayList<Condicion> condiciones) {
    this.condiciones = new ArrayList<>();
    this.condiciones.addAll(condiciones);
  }

  public boolean validate(String posiblePassword) {
    for (Condicion condicion : condiciones) {
      if (!condicion.validar(posiblePassword)) {
        return false;
      }
    }
    return true;
  }

}
