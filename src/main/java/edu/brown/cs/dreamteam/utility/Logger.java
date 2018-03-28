package edu.brown.cs.dreamteam.utility;

/**
 * A Logger utility class that logs to stdout different pieces of information.
 *
 * @author peter
 *
 */
public final class Logger {

  private Logger() {

  }

  private static boolean debug = false;

  public static void debug(boolean setDebug) {
    debug = setDebug;
  }

  public static void logDebug(String message) {
    if (debug) {
      System.out.println(message);

    }
  }

  public static void logMessage(String message) {
    System.out.println(message);

  }

  public static void logMessage(String message, Object... args) {
    System.out.printf(message, args);
  }

  /**
   * Prints to Console, errors of the form "ERROR: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logError(String message) {
    System.out.println("ERROR: " + message);
  }

  /**
   * Prints to Console, warnings of the form "WARNING: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logWarning(String message) {
    System.out.println("WARNING: " + message);
  }

  /**
   * Prints to Console, TestFails of the form "TEST FAIL: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logTestFail(String message) {
    System.out.println("TEST FAIL: " + message);
  }

}
