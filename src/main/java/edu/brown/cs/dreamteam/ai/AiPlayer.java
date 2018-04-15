package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.entity.DynamicEntity;

public class AiPlayer extends DynamicEntity {
  private boolean isAlive;

  /**
   * Constructs an AI player.
   */
  public AiPlayer(String id, double x, double y, double size) {
    super(id, x, y, size);
    this.setType("AI");
    isAlive = true;
  }

  @Override
  public void tick() {

  }

  @Override
  public void kill() {
    isAlive = false;
  }

  /**
   * Returns if the player is alive at any given point
   * 
   * @return true if the player is alive, false otherwise
   */
  public boolean isAlive() {
    return isAlive;
  }

}
