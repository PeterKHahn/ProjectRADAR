package edu.brown.cs.dreamteam.event;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.game.ChunkMap;

public class GameEventEmitter {

  Collection<GameEventListenerThread> listeners;

  public GameEventEmitter() {
    init();
  }

  private void init() {
    listeners = new LinkedList<GameEventListenerThread>();
  }

  public void addGameEventListener(GameEventListener listener) {
    listeners.add(new GameEventListenerThread(listener));
  }

  public void emit(ChunkMap chunks) {
    System.out.println("here");
    for (GameEventListenerThread listener : listeners) {
      listener.setChunk(chunks);
      new Thread(listener).start();

    }
  }

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
