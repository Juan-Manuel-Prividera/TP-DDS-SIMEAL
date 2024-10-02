package ar.edu.utn.frba.dds.simeal.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    // Method to check a password against a stored hashed password
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt BCrypt = new BCrypt();
        return org.mindrot.jbcrypt.BCrypt.checkpw(password, hashedPassword);
    }

}
