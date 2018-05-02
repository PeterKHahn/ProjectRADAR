package edu.brown.cs.dreamteam.ai;

import com.google.gson.Gson;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Interactable;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * A class that represents an AI player in the game.
 *
 * @author efu2
 */
public class AiPlayer extends DynamicEntity {
  private boolean isAlive;

  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE
  }

  private static final Gson GSON = new Gson();
  private AiController controller;

  /**
   * Constructs an AI player.
   */
  public AiPlayer(String id, double x, double y, double size) {
    super(id, x, y, size);
    this.setType("AI");
    isAlive = true;
  }

  public void setController(AiController controller) {
    this.controller = controller;
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

  @Override
  public double reach() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    controller.makeNextMove(chunkMap);
    updatePosition(chunkMap);
  }

  @Override
  public BoxSet hitBox() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double baseDamage() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public BoxSet hurtBox() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void getHit(HitBoxed hitBoxed) {
    // TODO Auto-generated method stub

  }

  @Override
  public void hit(Interactable e) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean hits(Interactable e) {
    // TODO Auto-generated method stub
    return false;
  }

}
