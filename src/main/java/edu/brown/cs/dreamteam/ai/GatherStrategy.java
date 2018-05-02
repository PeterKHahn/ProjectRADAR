package edu.brown.cs.dreamteam.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Playable;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
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
      // Reached goal
      if (goal != null && canPickItem(chunks)) {
        player.setItemPickedFlag(true);
      }
      // AI player doesn't have enough material to make a radar
      Set<Item> items = ChunkMap.itemsFromChunks(chunks);
      if (items.size() > 0) {
        // Get position of closest item
        goal = getGoalItemPosition(items);
      } else {
        // No items in visible range

        // Choose a random direction to go in if the goal is not already set
        // or if the goal position is reached
        if (goal == null || reachedGoal()) {
          Vector dir = new Vector(10 * (Math.random() - 0.5),
              10 * (Math.random() - 0.5));
          goal = board.getEdgePosition(getCurrentPosition(), dir);
          // System.out
          // .println("AI " + player.getId() + " new goal " + goal.toString());
        }
      }
    }
    moveTo(goal);
  }

  private boolean reachedGoal() {
    if (goal.distance(player.center()) < 1) {
      return true;
    }
    return false;
  }

  private boolean canPickItem(Collection<Chunk> chunks) {
    Collection<Item> items = ChunkMap.itemsFromChunks(chunks);
    Item closest = null;
    for (Item i : items) {
      if (closest == null) {
        closest = i;
      } else {
        closest = i.center().distance(player.center()) < closest.center()
            .distance(player.center()) ? i : closest;
      }
    }
    if (closest != null && closest.center()
        .distance(player.center()) < Playable.ITEM_PICK_RANGE) {
      return true;
    }
    return false;
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
