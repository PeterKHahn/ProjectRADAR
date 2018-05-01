package edu.brown.cs.dreamteam.ai;

import java.util.Collection;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.game.Chunk;

/**
 * Strategy to reach the revealed goal. Goal has been revealed! Go straight
 * towards the goal while attacking indiscriminately the whole time (to maximize
 * chance of reaching the goal alive and of killing enemies on the way).
 *
 * @author efu2
 */
public class GoalStrategy extends Strategy {

  public GoalStrategy(Board board, AiPlayer player) {
    super(board, player);
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    // TODO get goal
    Position goal = new Position(0, 0);
    moveTo(goal);

    // TODO Attack
  }

}
