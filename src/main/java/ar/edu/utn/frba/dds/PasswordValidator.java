package ar.edu.utn.frba.dds;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;


public class PasswordValidator {
  private File passwordBlacklist;
  private final int minimumLength = 8;

  // Safe to pass null.
  PasswordValidator(String pathToPasswordBlacklist) {
    if (pathToPasswordBlacklist != null) {
      // Once we check the path is not null, we also check it is actually a valid file.
      // If it isn't we print a warning a set the passwordFile to null.
      passwordBlacklist = new File(pathToPasswordBlacklist);
      if (!passwordBlacklist.isFile()) {
        System.out.println("Given path is not a valid file! blacklist test will not be conducted.");
        passwordBlacklist = null;
      }
    } else {
      // If the path is null, we warn the user that the blacklist test will not be conducted.
      System.out.println("Blacklist path is null, blacklist test will not be conducted!");
      passwordBlacklist = null;
    }
  }

  private boolean lengthTest(String potentialPassword) {
    return potentialPassword.length() >= minimumLength;
  }

  // Returns true if "potentialPassword" does not match any line in the "blacklistFile" OR if the file is missing.
  private boolean blacklistTest(String potentialPassword) {
    if (passwordBlacklist == null) {
      return true;
    }

    try {
      Scanner scanner = new Scanner(passwordBlacklist);

      while (scanner.hasNextLine()) {
        if (Objects.equals(scanner.nextLine(), potentialPassword)) {
          return false;
        }
      }
    } catch (FileNotFoundException e) {
      // It should never reach this block, we validated the blacklist path in the constructor... but who knows.
      System.out.printf("Exception: " + e.getMessage());
      return true;
    }
    return true;
  }
  // Returns true if "potentialPassword" is a valid password.

  public boolean validate(String potentialPassword) {
    return lengthTest(potentialPassword) && blacklistTest(potentialPassword);
  }

}
