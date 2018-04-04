package edu.brown.cs.dreamteam.event;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * An interface for elements who wish to be notified asynchronously when the
 * Game Finishes a tick and updates its state
 * 
 * @author peter
 *
 */
public interface GameEventListener {

  /**
   * The Action to perform with the given ChunkMap at the end of each tick
   * 
   * @param chunks
   *          The ChunkMap from the GameEventEmitter
   */
  public void onGameChange(ChunkMap chunks);

}
