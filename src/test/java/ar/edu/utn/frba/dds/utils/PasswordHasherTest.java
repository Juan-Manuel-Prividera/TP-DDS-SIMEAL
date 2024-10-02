package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.utils.PasswordHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordHasherTest {

    @Test
    public void hashearYVerificar() {
        String password = "Java == js";
        String hash = PasswordHasher.hashPassword(password);

        Assertions.assertTrue(PasswordHasher.checkPassword(password, hash));
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
    }

}
