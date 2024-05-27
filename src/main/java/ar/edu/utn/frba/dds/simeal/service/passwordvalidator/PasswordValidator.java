package ar.edu.utn.frba.dds.simeal.service.passwordvalidator;

import java.util.ArrayList;


public class PasswordValidator {
  ArrayList<Condicion> condiciones;
  String mensajeError;


  public PasswordValidator(ArrayList<Condicion> condiciones) {
    this.condiciones = new ArrayList<>();
    this.condiciones.addAll(condiciones);
    this.mensajeError = "Contraseña invalidad";
  }

  public boolean validate(String posiblePassword) {
    for (Condicion condicion : condiciones) {
      if (!condicion.validar(posiblePassword)) {
        System.out.println(mensajeError);
        return false;
      }
    }
    return true;
  }

}
