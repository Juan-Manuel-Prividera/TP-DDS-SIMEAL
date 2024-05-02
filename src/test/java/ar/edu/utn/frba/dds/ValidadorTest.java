package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.service.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorTest {

    PasswordValidator validador;

    @Test @DisplayName("Si la password es suficientemente larga y no esta en la blackList => Es valida")
    public void testIsLongEnough(){
        validador = new PasswordValidator("blacklist.txt");
        Assertions.assertTrue(validador.validate("passwordLarga"));
    }

    @Test @DisplayName("Si las passowrd no es lo suficientemente larga => No es valida")
    public void testIsNotLongEnough(){
        validador = new PasswordValidator("blacklist.txt");
        Assertions.assertFalse(validador.validate("lol"));
    }

    @Test @DisplayName("Si se iniciliza el validador con un path correcto al blackList y la password cumple ambas condiciones => Es valida")
    public void testWithBlackList() {
        validador = new PasswordValidator("blacklist.txt");
        Assertions.assertTrue(validador.validate("4prt67os02kd"));
    }

    @Test @DisplayName("Si no se pasa un path de la blackList pero la password es suficientemente larga => Es valida")
    public void testWithoutBlackList() {
        validador = new PasswordValidator(null);
        Assertions.assertTrue(validador.validate("password"));
    }

    @Test @DisplayName("Si se pasa un path a un archivo que no existe pero la password es suficientemente larga => Es valida")
    public void withWrongBlacklistPath() {
        validador = new PasswordValidator("NotAnExistingFile.txt");
        Assertions.assertTrue(validador.validate("password"));
    }

    @Test @DisplayName("Si la password es suficientemente larga y no esta en la blackList => Es valida")
    public void isValidPassword(){
        validador = new PasswordValidator("blacklist.txt");
        Assertions.assertTrue(validador.validate("AguanteLaUTNloco"));
    }

    @Test @DisplayName("Si la password es suficientemente larga pero esta en la blackList => No es valida")
    public void isNotAValidPassword(){
        validador = new PasswordValidator("blacklist.txt");
        Assertions.assertFalse(validador.validate("password"));
    }

}