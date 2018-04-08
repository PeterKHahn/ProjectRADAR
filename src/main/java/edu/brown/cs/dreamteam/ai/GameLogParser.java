package edu.brown.cs.dreamteam.ai;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;


/**
 * Parses game logs into lists of data samples for machine learning.
 *
 * @author efu2
 */
public class GameLogParser {
  private List<LogEntry> entries;
  private boolean isValid;

  /**
   * Instantiates a parser to parse the given game log.
   *
   * @param path
   *          The path to the game log.
   */
  public GameLogParser(String path) {
    entries = new ArrayList<>();
    isValid = false;
    readFile(path);
  }

  private void readFile(String path) {
    // TODO read game log file and update isValid

    // TODO convert each line into a LogEntry
    int numLines = 0;
    List<LogEntry> entries = new ArrayList<>();
    for (int i = 0; i < numLines; i++) {
      entries.add(new LogEntry("", ImmutableList.of(0, 0)));
    }
  }

  public boolean isValid() {
    return isValid;
  }

  /**
   * Gets all lines in the game log as a List.
   *
   * @return A List of LogEntry objects representing each line in the game log.
   */
  public List<LogEntry> getEntries() {
    return entries;
  }
}
