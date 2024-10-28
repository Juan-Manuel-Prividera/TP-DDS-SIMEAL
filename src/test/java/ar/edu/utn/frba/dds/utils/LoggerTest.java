package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LogType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
  Logger logger;

  @Test
  public void logToStdout()
  {
    Logger.trace("This is a trace message");
    Logger.debug("This is DEBUG information");
    Logger.info("This is some INFO");
    Logger.warn("This is a WARN!");
    Logger.error("This is an ERROR!!");
    Logger.fatal("This is a FATAL!");
  }


}
