package edu.brown.cs.dreamteam.ai;

/**
 * <p>
 * Strategy to attack other players to get their items. Activated when: AI
 * encounters another player and health is > 2 times any encountered players'
 * attack damage, and AI's hit range is larger than target player's hit range.
 * 
 * Steps until the AI kills the target or switches to a different mode: 1.
 * Choose the closest encountered player whose hit range is smaller than AI's
 * hit range as the target. 2. Move towards the target's current position to get
 * within hit range 3. Predict the other player's movements to aim and attack at
 * the optimal direction to hit the player 4. If successfully killed the target,
 * collect all target's better-quality items 5. Choose another target if the
 * mode hasn't changed yet
 * </p>
 * 
 * @author efu2
 */
public class OffensiveStrategy implements Strategy {
  private PositionPredictor pp;

  public OffensiveStrategy() {
    pp = new PositionPredictor();
  }

  @Override
  public void getNextMove(String entities) {
    // TODO: pass in enemy player's representation, check return type needed by
    // networking
    pp.nextPosition("");
  }

}
