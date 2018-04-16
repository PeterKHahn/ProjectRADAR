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

  /**
   * Sets the debug state to the specified value. Setting to this value to true
   * will print statements using logDebug(String). If the value is set to false,
   * logDebug(String) will have no effect.
   *
   * @param setDebug
   *          The value to set the debugger to.
   */
  public static void debug(boolean setDebug) {
    debug = setDebug;
  }

  /**
   * Logs a debug message. The message will print if and only if debug mode is
   * set to true.
   *
   * @param message
   *          The message to print
   */
  public static void logDebug(String message) {
    if (debug) {
      // System.out.println(message);

    }
  }

  /**
   * Prints a message to output.
   *
   * @param message
   *          THe message to print
   */
  public static void logMessage(String message) {
    // System.out.println(message);

  }

  /**
   * Prints a formatted string with arguements to output.
   *
   * @param message
   *          The formatted string to print
   * @param args
   *          The objects that fit the formatted string.
   */
  public static void logMessage(String message, Object... args) {
    // System.out.printf(message, args);
  }

  /**
   * Prints to Console, errors of the form "ERROR: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logError(String message) {
    // System.out.println("ERROR: " + message);
  }

  /**
   * Prints to Console, warnings of the form "WARNING: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logWarning(String message) {
    // System.out.println("WARNING: " + message);
  }

  /**
   * Prints to Console, TestFails of the form "TEST FAIL: [message]".
   *
   * @param message
   *          the message associated with the error
   */
  public static void logTestFail(String message) {
    // System.out.println("TEST FAIL: " + message);
  }

}
