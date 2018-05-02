package edu.brown.cs.dreamteam.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.item.Item;

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
      Set<Item> items = board.chunks().itemsFromChunks(chunks);
      if (items.size() > 0) {
        System.out.println("AI " + player.getId() + " sees items!");
        // Get position of closest item
        goal = getGoalItemPosition(items);
        System.out.println(
            "AI " + player.getId() + " set goal to " + goal.toString());
      } else {
        // No items in visible range

        // Choose a random direction to go in if the goal is not already set
        // or if the goal position is reached
        if (goal == null || player.center().equals(goal)) {
          Vector dir = new Vector(10 * (Math.random() - 0.5),
              10 * (Math.random() - 0.5));
          goal = board.getEdgePosition(getCurrentPosition(), dir);
          System.out.println("AI " + player.getId() + " set goal to "
              + goal.toString() + " with direction " + dir.toString());
        }
        System.out
            .println("AI " + player.getId() + " moving to " + goal.toString());
      }
    }
    moveTo(goal);
  }

  /**
   * Gets the closest item's position. Assumes that there are items in the given
   * set.
   * 
   * @return The closest item's position.
   */
  private Position getGoalItemPosition(Set<Item> items) {
    Vector closest = new Vector(0, 0);
    double distance = Double.MAX_VALUE;
    Iterator<Item> it = items.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      Vector center = item.center();
      double currDistance = center.distance(player.center());
      if (currDistance < distance) {
        distance = currDistance;
        closest = center;
      }
    }
    return new Position(closest.x, closest.y);
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
