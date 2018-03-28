package edu.brown.cs.dreamteam.main;

import java.util.Map;

import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventListener;

public abstract class Architect implements Runnable, GameEventListener {

  public abstract Map<String, ClientState> retrieveClientStates();

}
