package edu.brown.cs.dreamteam.event;

import edu.brown.cs.dreamteam.game.ChunkMap;

public interface GameEventListener {

  public void onGameChange(ChunkMap chunks);

}
