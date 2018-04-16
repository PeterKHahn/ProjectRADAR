package edu.brown.cs.dreamteam.event;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * GameEventEmitter will emit at the End of every tick, information about the
 * game state, using the observer pattern
 *
 * @author peter
 *
 */
public class GameEventEmitter {

  private Collection<GameEventListenerThread> listeners;

  public GameEventEmitter() {
    init();
  }

  private void init() {
    listeners = new LinkedList<GameEventListenerThread>();
  }

  /**
   * Adds a GameEventListener to the collection of listeners who will listen for
   * this signal
   *
   * @param listener
   */
  public void addGameEventListener(GameEventListener listener) {
    listeners.add(new GameEventListenerThread(listener));
  }

  /**
   * Emits information about a ChunkMap to all GameEventListeners in the thread.
   * It does this asynchronously
   *
   * @param chunks
   *          the ChunkMap to emit
   */
  public void emit(ChunkMap chunks) {
    System.out.println("emmitting...");
    for (GameEventListenerThread listener : listeners) {
      System.out.println("listener hit");
      listener.setChunk(chunks);
      new Thread(listener).start();

    }
  }

  /**
   * A wrapper class to support multithreading for each of the listeners
   *
   * @author peter
   *
   */
  private class GameEventListenerThread implements Runnable {

    private GameEventListener listener;
    private ChunkMap chunks;

    private GameEventListenerThread(GameEventListener listener) {
      this.listener = listener;

    }

    private void setChunk(ChunkMap chunks) {
      this.chunks = chunks;
    }

    @Override
    public void run() {
      listener.onGameChange(chunks);

    }

  }

}
