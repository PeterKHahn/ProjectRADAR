package edu.brown.cs.dreamteam.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;

public class Architect implements Runnable, GameEventListener {

  private GameEngine game;

  private Set<ClientState> clientStates;

  public Architect() {
    init();
    run();
  }

  private void init() {
    game = new GameEngine(this);
    clientStates = Sets.newConcurrentHashSet();
  }

  @Override
  public void run() {
    System.out.println("Hello There");
    Map<String, Thread> threads = threads();

    for (Thread t : threads.values()) {
      t.start();
    }

  }

  /**
   * Returns a thread safe set of ClientStates.
   *
   * @return
   */
  public Set<ClientState> retrieveClientStates() {

    return clientStates;
  }

  private Map<String, Thread> threads() {
    Map<String, Thread> threads = new HashMap<>();
    threads.put("game", new Thread(game, "game"));

    return threads;
  }

  @Override
  public void onGameChange(ChunkMap chunks) {
    System.out.println("Event recieved: " + chunks);

  }

}
