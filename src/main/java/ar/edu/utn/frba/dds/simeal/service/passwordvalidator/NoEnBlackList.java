package ar.edu.utn.frba.dds.simeal.service.passwordvalidator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class NoEnBlackList implements Condicion {
  File blackList;

  // Safe to pass null.
  public NoEnBlackList(String pathToPasswordBlacklist) {
    if (pathToPasswordBlacklist != null) {
      // Once we check the path is not null, we also check it is actually a valid file.
      // If it isn't we print a warning a set the passwordFile to null.
      blackList = new File(pathToPasswordBlacklist);
      if (!blackList.isFile()) {
        System.out.println("Given path is not a valid file! blacklist test will not be conducted.");
        blackList = null;
      }
    } else {
      // If the path is null, we warn the user that the blacklist test will not be conducted.
      System.out.println("Blacklist path is null, blacklist test will not be conducted!");
      blackList = null;
    }
  }

  @Override
  public boolean validar(String posiblePassword) {
    if (blackList == null) {
      return true;
    }

    try {
      Scanner scanner = new Scanner(blackList, StandardCharsets.UTF_8);

      while (scanner.hasNextLine()) {
        if (Objects.equals(scanner.nextLine(), posiblePassword)) {
          return false;
        }
      }
    } catch (IOException e) {
      // It should never reach this block, we validated the blacklist
      // path in the constructor... but who knows.
      System.out.printf("Exception: " + e.getMessage());
      return true;
    }
    return true;
  }
}
