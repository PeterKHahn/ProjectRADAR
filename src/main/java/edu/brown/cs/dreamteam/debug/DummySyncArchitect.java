package edu.brown.cs.dreamteam.debug;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.main.Architect;
import edu.brown.cs.dreamteam.utility.Logger;

/**
 * A Dummy system architect that tests simple features concurrently.
 * 
 * @author peter
 *
 */
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

  }

  // @Override
  // public void onGameChange(ChunkMap chunks) {
  //
  // }

  public Map<String, ClientState> retrieveClientStates() {

    ClientState tmp = clientStates.get("0");
    tmp.forwardHeld(true);
    return clientStates;
  }

  private Collection<GamePlayer> generatePlayers(int numPlayers) {
    Collection<GamePlayer> res = new LinkedList<GamePlayer>();
    for (int i = 0; i < numPlayers; i++) {
      GamePlayer player = GamePlayer.player("151" + i, 3 * Math.random(),
          3 * Math.random());
      res.add(player);
    }

    return res;
  }

  public void putClientState(String name, ClientState state) {
    // TODO Auto-generated method stub

  }

  @Override
  public void initSpark() {
    // TODO Auto-generated method stub

  }

  public void onGameChange(ChunkMap chunks) {
    // TODO Auto-generated method stub

  }

}
