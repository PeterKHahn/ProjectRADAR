package edu.brown.cs.dreamteam.event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * GameEventEmitter will emit at the End of every tick, information about the
 * game state, using the observer pattern
 *
 * @author peter
 *
 */
public class GameEventEmitter {
  private static AtomicInteger num = new AtomicInteger();
  private Collection<GameEventListener> listeners;

  public GameEventEmitter() {
    init();
  }

  private void init() {
    listeners = new LinkedList<GameEventListener>();
  }

  /**
   * Adds a GameEventListener to the collection of listeners who will listen for
   * this signal
   *
   * @param listener
   */
  public void addGameEventListener(GameEventListener listener) {
    listeners.add(listener);
  }

  /**
   * Emits information about a ChunkMap to all GameEventListeners in the thread.
   * It does this asynchronously
   *
   * @param chunks
   *          the ChunkMap to emit
   */
  public void emit(ChunkMap chunks) {
    for (GameEventListener listener : listeners) {
      listener.onGameChange(chunks);

    }
  }

}
