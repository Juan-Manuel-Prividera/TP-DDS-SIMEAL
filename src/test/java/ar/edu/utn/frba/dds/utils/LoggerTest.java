package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import org.junit.jupiter.api.Test;

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
    //Logger.fatal("This is a FATAL!");
  }


}
