package edu.brown.cs.dreamteam.main;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.utility.Logger;

/**
 * The Main Architect for the project, where all functionality integration the
 * many components exist
 * 
 * @author peter
 *
 */
public class SystemArchitect extends Architect {

  private GameEngine game;

  private Map<String, ClientState> clientStates;

  public SystemArchitect() {
    init();
  }

  private void init() {
    game = new GameEngine(this);
    game.addGameEventListener(this);
    clientStates = Maps.newConcurrentMap();
  }

  @Override
  public void run() {
    Logger.logMessage("Architect is now running");
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
  public Map<String, ClientState> retrieveClientStates() {

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
