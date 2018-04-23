package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.game.Chunk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
    List<DynamicEntity> enemies = getVisibleEnemies(chunks);
    Position goal = getEscapePosition(enemies, chunks);
    // Get player's Position in the graph
    Position currPos = board.getPosition((int) Math.floor(player.getXPos()),
        (int) Math.floor(player.getYPos()));
    Position next = board.getMoveTo(currPos, goal);
    // TODO Update player to go in direction of next

  }

  private Position getEscapePosition(List<DynamicEntity> enemies,
      Collection<Chunk> chunks) {
    int numEnemies = enemies.size();
    double escapeX = 0;
    double escapeY = 0;
    for (int i = 0; i < numEnemies; i++) {
      escapeX += -enemies.get(i).getXVelocity();
      escapeY += -enemies.get(i).getYVelocity();
    }

    // Convert the values to a valid direction coefficient
    int xDir = escapeX > 0 ? (escapeX < 0 ? -1 : 0) : 1;
    int yDir = escapeY > 0 ? (escapeY < 0 ? -1 : 0) : 1;

    // The enemies' directions cancelled each other out
    while (Integer.compare(xDir, 0) == 0 && Integer.compare(yDir, 0) == 0) {
      // Pick a random direction to go in
      List<Integer> values = new ArrayList<>();
      values.add(-1);
      values.add(0);
      values.add(1);

      Random r = new Random();
      escapeX = values.get(r.nextInt(3));
      escapeY = values.get(r.nextInt(3));
    }

    Position goal = board.getPosition(AiController.VISIBLE_RANGE * xDir,
        AiController.VISIBLE_RANGE * yDir);

    // TODO what if goal isn't traversable? Goal node must be at least player's
    // size positions away from obstacles
    if (goal == null || !board.isTraversable(goal)) {

    }
    return goal;
  }

}
