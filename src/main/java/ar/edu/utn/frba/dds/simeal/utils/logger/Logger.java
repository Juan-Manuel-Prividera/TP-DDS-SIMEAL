package ar.edu.utn.frba.dds.simeal.utils.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Logger {

  static String filename = "src/main/java/ar/edu/utn/frba/dds/simeal/log/log_de_todo.log";

  static Map<LogType, String> strings = Map.of(
          LogType.TRACE, ".",
          LogType.DEBUG, "-",
          LogType.INFO, "i",
          LogType.WARN, "!",
          LogType.ERROR, "E",
          LogType.FATAL, "FATAL"
  );

  static Map<LogType, String> colors = Map.of(
          LogType.TRACE, "grey",
          LogType.DEBUG, "white",
          LogType.INFO, "green",
          LogType.WARN, "yellow",
          LogType.ERROR, "red",
          LogType.FATAL, "bright_red"
  );

  static Map<String, String> sequences = Map.of(
          "reset",      "\033[0m",
          "bold",       "\033[1m",
          "grey",       "\033[90m",
          "red",        "\033[31m",
          "bright_red", "\033[91m",
          "green",      "\033[32m",
          "yellow",     "\033[33m",
          "blue",       "\033[34m",
          "cyan",       "\033[35m",
          "white",      "\033[0m"
  );

  static private String color(LogType type) {
    return sequences.get(colors.get(type));
  }

  public static void trace(String format, Object... args) {
    log(LogType.TRACE, format, args);
  }

  public static void debug(String format, Object... args) {
    log(LogType.DEBUG, format, args);
  }

  public static void info(String format, Object... args) {
    log(LogType.INFO, format, args);
  }

  public static void warn(String format, Object... args) {
    log(LogType.WARN, format, args);
  }

  public static void error(String format, Object... args) {
    log(LogType.ERROR, format, args);
  }

  public static void fatal(String format, Object... args) {
    log(LogType.FATAL, format, args);
    System.exit(1);
  }

  private static void log(LogType type, String format, Object... args) {
    LocalTime currentTime = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    String formattedLog = String.format(format, args);

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StackTraceElement caller = stackTrace[3];

    String trace = String.format("%s.%s():%s",
            caller.getClassName(),
            caller.getMethodName(),
            caller.getLineNumber()).replaceFirst(".*dds\\.", "");

    String notColored = String.format("%s [%s] %s \t -> %s", formattedTime, strings.get(type), formattedLog, trace);
    String colored = String.format("%s%s%s [%s] %s %s-> %s %s",
      sequences.get("grey"),
      formattedTime,
      color(type),
      strings.get(type),
      formattedLog,
      sequences.get("grey"),
      trace,
      sequences.get("reset")
    );

    System.out.println(colored);

    try {
      FileWriter fileWriter = new FileWriter(filename, true);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.println(notColored);
      printWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return;

  }
}
