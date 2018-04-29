package edu.brown.cs.dreamteam.game;

import java.util.Collection;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;

public class EntityFactory {

  private ChunkMap chunks;

  public EntityFactory(ChunkMap chunks) {
    this.chunks = chunks;
  }

  public void addObstacle(Obstacle entity) {
    Collection<Chunk> chunksInRange = chunks.chunksInRange(entity);
    chunks.addStatic(entity, chunksInRange);
  }

  public void addPlayer(GamePlayer entity) {
    Collection<Chunk> chunksInRange = chunks.chunksInRange(entity);
    chunks.addPlayer(entity, chunksInRange);
  }

}
