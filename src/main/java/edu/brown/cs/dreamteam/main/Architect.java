package edu.brown.cs.dreamteam.main;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.game.GameEngine;

public class Architect implements Runnable, GameEventListener {

  private GameEngine game;

  public Architect() {
    init();
    run();
  }

  private void init() {
    game = new GameEngine();
  }

  @Override
  public void run() {
    System.out.println("Hello There");
    Map<String, Thread> threads = threads();

    for (Thread t : threads.values()) {
      t.start();
    }

  }

  private Map<String, Thread> threads() {
    Map<String, Thread> threads = new HashMap<>();
    threads.put("game", new Thread(game, "game"));

    return threads;
  }

  @Override
  public void onGameChange() {
    System.out.println("Event recieved");

  }

}
