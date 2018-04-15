package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.event.ClientState;
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
public class DefensiveStrategy implements Strategy {

  public DefensiveStrategy() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public ClientState makeNextMove(ChunkMap chunks, ClientState currState) {
    // TODO Auto-generated method stub
    return null;
  }

}
