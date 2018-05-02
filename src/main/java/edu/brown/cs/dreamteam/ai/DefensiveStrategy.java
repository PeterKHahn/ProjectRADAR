package edu.brown.cs.dreamteam.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * Strategy to avoid all enemy players to save health. If AI's health is below 2
 * times any encountered player's attack damage, it will prioritize fleeing from
 * other players. Cases: - One encountered player: predict the player's next
 * position and go in the same direction as that to run away. - Several
 * encountered players: choose a direction by: 1. Calculating the 180 degree
 * range of directions (x and y range) for each player, with the player's
 * predicted direction at the right angle position (so it's a 180 degree fan
 * with the predicted direction as the angle bisector) 2. Choose a random
 * direction in the overlaps of all other players' 180 degree fans. If there are
 * no overlaps (e.g. a perfect surrounding attack by other players), randomly
 * choose angle not directly opposite to any of the player's angles and
 * reevaluate at the next update.
 * 
 * @author efu2
 */
public class DefensiveStrategy extends Strategy {

  public DefensiveStrategy(Board board, AiPlayer player) {
    super(board, player);
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    // Get the goal node to run to
    Position goal = getEscapeGoal(chunks);
    moveTo(goal);

    // TODO Attack while running away?
  }

  private Position getEscapeGoal(Collection<Chunk> chunks) {
    // Determine the direction to escape in
    Vector escape = getEscapeDir(chunks);
    Position curr = getCurrentPosition();
    board.addEdgesFor(curr, false);

    // Get the next position in the shortest path to the furthest position in
    // the escape direction
    return board.getEdgePosition(curr, escape);
  }

  private Vector getEscapeDir(Collection<Chunk> chunks) {
    // Get all enemies in visible range
    Set<DynamicEntity> enemies = ChunkMap.dynamicFromChunks(chunks);
    enemies.remove(player);

    // Determine escape direction
    double escapeX = 0;
    double escapeY = 0;
    Iterator<DynamicEntity> it = enemies.iterator();
    while (it.hasNext()) {
      DynamicEntity enemy = it.next();
      escapeX += enemy.getXVelocity();
      escapeY += enemy.getYVelocity();
    }

    // The enemies' directions cancelled each other out
    if (Double.compare(escapeX, 0) == 0 && Double.compare(escapeY, 0) == 0) {
      // TODO Pick a random map border node to go to
    }

    return new Vector(escapeX, escapeY);
  }

}
