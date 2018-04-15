package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.Chunk;
import java.util.Collection;

/**
 * Strategy to reach the revealed goal. Goal has been revealed! Go straight
 * towards the goal in modified defensive mode: Instead of fleeing the opposite
 * direction the encountered player is coming from, flee in the direction
 * closest to the goal but still avoids the encountered players' hit box.
 *
 * @author efu2
 */
public class GoalStrategy extends Strategy {

  public GoalStrategy(Board board, AiPlayer player) {
    super(board, player);
    // TODO Auto-generated constructor stub
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    // TODO Auto-generated method stub

  }

}
