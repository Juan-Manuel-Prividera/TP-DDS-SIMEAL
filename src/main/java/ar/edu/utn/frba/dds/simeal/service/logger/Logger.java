package ar.edu.utn.frba.dds.simeal.service.logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
  String filename;
  static Logger instance;

  static public Logger getInstance(String filename) {
    instance = new Logger(filename);
    return instance;
  }

  static public Logger getInstance() {
    instance = new Logger(null);
    return instance;
  }

  public void log(LoggerType type, String str) {
    LocalTime currentTime = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    String out = this.typeString(type) + " " + formattedTime + " - " + str;

    if (filename != null) {
      try {
        FileWriter fileWriter = new FileWriter(this.filename, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(out);
        printWriter.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println(out);
    }
  }
  private Logger (String file) {
    this.filename = file;
  }

  private String typeString(LoggerType type) {
    String ret;

    switch (type) {
      case WARNING -> { ret = "[W]"; }
      case DEBUG -> { ret = "[D]"; }
      case ERROR -> { ret = "   -> [E]"; }
      case INFORMATION -> { ret = "[I]"; }
      default -> { ret = "[]"; }
    }

    return ret;
  }
}
