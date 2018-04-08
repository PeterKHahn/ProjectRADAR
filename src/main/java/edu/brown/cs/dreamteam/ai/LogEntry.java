package edu.brown.cs.dreamteam.ai;

import java.util.List;

/**
 * Represents a row in the game log, which should be one player's information at
 * the time the log was produced.
 *
 * @author efu2
 */
public class LogEntry {
  private String id;
  private List<Integer> coords;

  /**
   * Represents a row in a game log.
   *
   * @param id
   *          The ID of the player in this game.
   * @param coords
   *          The coordinates of the player on the board.
   */
  public LogEntry(String id, List<Integer> coords) {
    this.id = id;
    this.coords = coords;
  }

  /**
   * Getter for this player's ID.
   * 
   * @return A String representing the player's ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Getter for this player's board coordinates.
   * 
   * @return A List of 2 Integers representing the player's coordinates.
   */
  public List<Integer> getCoords() {
    return coords;
  }

}
