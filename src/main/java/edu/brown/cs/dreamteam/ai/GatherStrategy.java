package edu.brown.cs.dreamteam.ai;

import java.util.Collection;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.Chunk;

/**
 * Strategy to gather as many items as possible. No other players are in the
 * visible range. Cases: - AI currently has insufficient materials to make a
 * radar - Get all the best-quality items in the visible range - If no items are
 * in the visible range, choose a random direction and continue in that
 * direction until encountering items or other players (and change modes
 * accordingly) - AI has sufficient materials to make a radar - If any
 * better-quality items are in the visible range, pick them up - Otherwise, make
 * a radar and decide its placement - If no radars have been placed yet, place
 * it right where the AI is - If one radar has been placed, place it somewhere
 * such that the radar radii don't overlap - If multiple radars have been
 * placed, go towards the midpoint of existing radars' centers and place the
 * radar there
 * 
 * @author efu2
 */
public class GatherStrategy extends Strategy {
  private Position goal;

  public GatherStrategy(Board board, AiPlayer player) {
    super(board, player);
  }

  public void reset() {
    goal = null;
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    if (canMakeRadar()) {
      // AI player has enough material to make a radar
      goal = placeRadar(chunks);
    } else {
      // AI player doesn't have enough material to make a radar
      // Get position of closest item
      // TODO Check # of visible items
      if (itemsInRange(chunks)) {
        goal = getGoalItemPosition(chunks);
      } else {
        // No items in visible range

        // Choose a random direction to go in if the goal is not already set
        if (goal == null) {
          Vector dir = new Vector(10 * (Math.random() - 0.5),
              10 * (Math.random() - 0.5));
          goal = board.getEdgePosition(getCurrentPosition(), dir);
        }
      }
    }
    moveTo(goal);
  }

  private boolean itemsInRange(Collection<Chunk> chunks) {
    // TODO
    return false;
  }

  private Position getGoalItemPosition(Collection<Chunk> chunks) {
    // TODO
    return new Position(0, 0);
  }

  private Position placeRadar(Collection<Chunk> chunks) {
    // TODO Check if any radars are already placed
    // if (radarsPlaced) {
    // } else {
    // TODO No other radars are placed, so just place it right here

    return new Position(0, 0);
  }

  /**
   * Checks whether the AI player has enough materials to make a radar.
   * 
   * @param entities
   *          The entity information given by networking.
   * @return True if there are sufficient materials, false otherwise.
   */
  private boolean canMakeRadar() {
    // TODO
    return false;
  }
}
