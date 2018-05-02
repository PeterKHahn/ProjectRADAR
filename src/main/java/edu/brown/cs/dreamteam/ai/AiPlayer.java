package edu.brown.cs.dreamteam.ai;

import com.google.gson.Gson;

import edu.brown.cs.dreamteam.entity.Playable;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * A class that represents an AI player in the game.
 *
 * @author efu2
 */
public class AiPlayer extends Playable {

  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE
  }

  private static final Gson GSON = new Gson();
  private transient AiController controller;

  /**
   * Constructs an AI player.
   */
  public AiPlayer(String id, double x, double y, double size) {
    super(id, x, y, "AI");
  }

  public void setController(AiController controller) {
    this.controller = controller;
  }

  public void setItemPickedFlag(boolean val) {
    itemPickedFlag = val;
  }

  public void setPrimaryActionFlag(boolean val) {
    primaryActionFlag = val;
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    controller.makeNextMove(chunkMap);
    super.tick(chunkMap);
  }

}
