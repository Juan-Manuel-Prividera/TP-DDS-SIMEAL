package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.Condicion;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.LongitudTest;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.NoEnBlackList;
import ar.edu.utn.frba.dds.simeal.utils.passwordvalidator.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ValidadorTest {

    PasswordValidator validador;
    ArrayList<Condicion> condiciones;

    NoEnBlackList siTieneBlackList = new NoEnBlackList("src/main/resources/blacklist.txt");
    NoEnBlackList noTieneBlackList = new NoEnBlackList(null);
    NoEnBlackList noExisteArchivo = new NoEnBlackList("noExisteEsteArchivo.txt");
    LongitudTest longitudTest = new LongitudTest(7);

    @BeforeEach
    public void init(){
        condiciones = new ArrayList<>();
        condiciones.add(longitudTest);
    }

    @Test @DisplayName("Si la password es suficientemente larga y no esta en la blackList => Es valida")
    public void testIsLongEnough(){
        validador = new PasswordValidator(condiciones);
        Assertions.assertNull(validador.validate("passwordLarga"));
    }

    @Test @DisplayName("Si las passowrd no es lo suficientemente larga => No es valida")
    public void testIsNotLongEnough(){
        validador = new PasswordValidator(condiciones);
        Assertions.assertNotNull(validador.validate("lol"));
    }

    @Test @DisplayName("Si se iniciliza el validador con un path correcto al blackList y la password cumple ambas condiciones => Es valida")
    public void testWithBlackList() {
        condiciones.add(siTieneBlackList);

        validador = new PasswordValidator(condiciones);
        Assertions.assertNull(validador.validate("4prt67os02kd"));
    }

    @Test @DisplayName("Si no se pasa un path de la blackList pero la password es suficientemente larga => Es valida")
    public void testWithoutBlackList() {
        condiciones.add(noTieneBlackList);

        validador = new PasswordValidator(condiciones);
        Assertions.assertNull(validador.validate("password"));
    }

    @Test @DisplayName("Si se pasa un path a un archivo que no existe pero la password es suficientemente larga => Es valida")
    public void withWrongBlacklistPath() {
        condiciones.add(noExisteArchivo);

        validador = new PasswordValidator(condiciones);
        Assertions.assertNull(validador.validate("password"));
    }

    @Test @DisplayName("Si la password es suficientemente larga y no esta en la blackList => Es valida")
    public void isValidPassword(){
        condiciones.add(siTieneBlackList);

        validador = new PasswordValidator(condiciones);
        Assertions.assertNull(validador.validate("AguanteLaUTNloco"));
    }

    @Test @DisplayName("Si la password es suficientemente larga pero esta en la blackList => No es valida")
    public void isNotAValidPassword(){
        condiciones.add(siTieneBlackList);

        validador = new PasswordValidator(condiciones);
        Assertions.assertNotNull(validador.validate("password"));
    }

}