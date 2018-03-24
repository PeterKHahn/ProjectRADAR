package edu.brown.cs.dreamteam.event;

import java.util.Collection;

import edu.brown.cs.dreamteam.game.ChunkMap;

public class GameEventEmitter {

  Collection<GameEventListenerThread> listeners;

  public void addGameEventListener(GameEventListener listener) {
    listeners.add(new GameEventListenerThread(listener));
  }

  public void emit(ChunkMap chunks) {
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
