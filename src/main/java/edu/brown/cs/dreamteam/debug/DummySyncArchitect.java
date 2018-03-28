package edu.brown.cs.dreamteam.debug;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.main.Architect;
import edu.brown.cs.dreamteam.utility.Logger;

public class DummySyncArchitect extends Architect {

  private Map<String, ClientState> clientStates;

  public DummySyncArchitect() {
    init();
  }

  private void init() {
    Logger.debug(true);
    clientStates = new HashMap<String, ClientState>();
  }

  @Override
  public void run() {
    Logger.logMessage("Dummy Architect is now running");
    GameEngine game = new GameEngine(this);
    Collection<GamePlayer> players = generatePlayers(1);
    for (GamePlayer player : players) {
      Logger.logDebug(player.getId());
      clientStates.put(player.getId(), new ClientState(player.getId()));
      game.addPlayer(player);
    }

    game.run();
  }

  @Override
  public void onGameChange(ChunkMap chunks) {
    System.out.println("Chunks: " + chunks);
    Chunk[][] array = chunks.getChunkArray();
    for (int r = 0; r < array.length; r++) {
      for (int c = 0; c < array[0].length; c++) {
        Logger.logDebug("Chunk at (" + r + ", " + c + ") " + array[r][c]);
      }
    }

  }

  @Override
  public Map<String, ClientState> retrieveClientStates() {

    ClientState tmp = clientStates.get("0");
    tmp.forwardHeld(true);
    return clientStates;
  }

  private Collection<GamePlayer> generatePlayers(int numPlayers) {
    Collection<GamePlayer> res = new LinkedList<GamePlayer>();
    for (int i = 0; i < numPlayers; i++) {
      GamePlayer player = new GamePlayer("" + i, 3 * Math.random(),
          3 * Math.random());
      res.add(player);
    }

    return res;
  }

}
