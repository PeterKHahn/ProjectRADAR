package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * Strategy to reach the revealed goal. Goal has been revealed! Go straight
 * towards the goal in modified defensive mode: Instead of fleeing the opposite
 * direction the encountered player is coming from, flee in the direction
 * closest to the goal but still avoids the encountered players' hit box.
 *
 * @author efu2
 */
public class GoalStrategy implements Strategy {

  public GoalStrategy() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void makeNextMove(ChunkMap chunks, AiPlayer player) {
    // TODO Auto-generated method stub
  }

}
