package edu.brown.cs.dreamteam.board;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * Represents the game board as a graph with Positions as vertices and Moves as
 * edges.
 *
 * @author efu2
 */
public class Board {

  /**
   * Constructs the graph using the given entity information at the beginning of
   * the game.
   *
   * @param entities
   *          All entities' positions on the game board.
   */
  public Board(ChunkMap chunks) {
    // TODO Auto-generated constructor stub
  }

  /**
   * Does A* Search between the given start and end position and gives the next
   * position that the player should go to to reach the end in the shortest
   * path.
   *
   * @param start
   *          The starting position.
   * @param end
   *          The destination position.
   * @return A position adjacent to the starting position that starts the
   *         shortest path to the destination.
   */
  public Position getMoveTo(Position start, Position end) {
    // TODO
    return null;
  }

}
