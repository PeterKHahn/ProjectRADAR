package edu.brown.cs.dreamteam.ai;

import java.util.Collection;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * <p>
 * Strategy to attack other players to get their items. Activated when: AI
 * encounters another player and health is > 2 times any encountered players'
 * attack damage, and AI has an item whose hit range is larger than target
 * player's hit range.
 *
 * Steps until the AI kills the target or switches to a different mode:
 * <ol>
 * <li>Choose the closest encountered player as the target.
 * <li>Move towards the target's current position to get within hit range
 * <li>Predict the other player's movements to aim and attack at the optimal
 * direction to hit the player
 * <li>If successfully killed the target, collect all target's better-quality
 * items
 * <li>Choose another target if the mode hasn't changed yet
 * </ol>
 * </p>
 * 
 * @author efu2
 */
public class OffensiveStrategy extends Strategy {
  public static final double BASELINE_HEALTH = 30;
  private DynamicEntity target;

  public OffensiveStrategy(Board board, AiPlayer player) {
    super(board, player);
    // TODO Auto-generated constructor stub
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    System.out.println("AI " + player.getId() + " offense");
    // Choose the closest encountered player as the target.
    if (target == null || !target.isAlive()) {
      chooseTarget(chunks);
    }

    // Move towards the target's current position to get within hit range
    Position goal = new Position(target.center().x, target.center().y);
    board.addEdgesFor(goal, true);
    board.addPosition(goal);

    // Attack
    player.setPrimaryActionFlag(true);
    moveTo(goal, true);

  }

  private void chooseTarget(Collection<Chunk> chunks) {
    Set<DynamicEntity> enemies = ChunkMap.getEnemies(player, chunks);
    double distance = Double.MAX_VALUE;
    for (DynamicEntity e : enemies) {
      Vector center = e.center();
      double currDistance = center.distance(player.center());
      if (currDistance < distance) {
        distance = currDistance;
        target = e;
      }
    }
  }

  private Position getTargetInHitBox() {
    return new Position(0, 0);
  }

  @Override
  public void reset() {
    target = null;
  }
}
