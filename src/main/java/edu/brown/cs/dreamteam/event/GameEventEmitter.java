package edu.brown.cs.dreamteam.event;

import java.util.Collection;

public class GameEventEmitter {

  Collection<GameEventListenerThread> listeners;

  public void addGameEventListener(GameEventListener listener) {
    listeners.add(new GameEventListenerThread(listener));
  }

  public void emit() {
    for (GameEventListenerThread listener : listeners) {
      new Thread(listener).start();

    }
  }

  private class GameEventListenerThread implements Runnable {

    private GameEventListener listener;

    private GameEventListenerThread(GameEventListener listener) {
      this.listener = listener;

    }

    @Override
    public void run() {
      listener.onGameChange();

    }

  }

}
