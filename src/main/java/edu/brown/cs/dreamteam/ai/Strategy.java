package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * An interface defining a game mode's strategy to control the AI player.
 *
 * @author efu2
 */
public interface Strategy {
  /**
   * Makes the next move based on the specific strategy's goals.
   *
   * @param chunks
   *          Information about all entities on the map.
   */
  void makeNextMove(ChunkMap chunks, AiPlayer player);
}
