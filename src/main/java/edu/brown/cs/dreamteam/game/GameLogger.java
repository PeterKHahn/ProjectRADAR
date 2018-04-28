package edu.brown.cs.dreamteam.game;

import java.util.Collection;

import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.event.GameEventListener;

public class GameLogger implements GameEventListener {

  private int fromRow = 0;
  private int toRow = 0;
  private int fromCol = 0;
  private int toCol = 0;

  public GameLogger(GameEngine ge) {
    ge.addGameEventListener(this);
  }

  @Override
  public void onGameChange(ChunkMap chunks, int id) {
    int tickCount = chunks.tickCount();
    Collection<Chunk> chunksInRange = chunks.chunksInRange(fromRow, toRow,
        fromCol, toCol);
    Collection<StaticEntity> staticEntities = chunks
        .staticFromChunks(chunksInRange);
    Collection<DynamicEntity> dynamicEntities = chunks
        .dynamicFromChunks(chunksInRange);
  }

}
