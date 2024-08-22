package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
  Logger logger;

  @Test
  public void logToFile()
  {
    String filename = "TEST_LOGGER.txt";
    logger = Logger.getInstance(filename);

    logger.log(LoggerType.DEBUG, "This is DEBUG information");
    logger.log(LoggerType.WARNING, "This is a WARNING!");
    logger.log(LoggerType.ERROR, "This is an ERROR!!");
    logger.log(LoggerType.INFORMATION, "This is plain INFORMATION");

    File logFile = new File(filename);
    try {
      assertTrue(logFile.exists(), "Log file should be created");

      List<String> lines = Files.readAllLines(Paths.get(filename));
      int expectedLines = 4;
      assertEquals(expectedLines, lines.size());

    } catch (IOException e) {
      e.getStackTrace();
    } finally {
      if (logFile.exists()) {
        assertTrue(logFile.delete());
      }
    }
  }

  // Esto no tiene un test la verdad, pero está bueno para ver el formato.
  @Test
  public void logToStdout()
  {
    logger = Logger.getInstance();

    logger.log(LoggerType.DEBUG, "This is DEBUG information");
    logger.log(LoggerType.WARNING, "This is a WARNING!");
    logger.log(LoggerType.ERROR, "This is an ERROR!!");
    logger.log(LoggerType.INFORMATION, "This is plain INFORMATION");

  }


}
