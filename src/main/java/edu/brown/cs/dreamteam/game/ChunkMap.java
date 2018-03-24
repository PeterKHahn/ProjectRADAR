package edu.brown.cs.dreamteam.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;

public class ChunkMap implements Tickable {

  private final int height;
  private final int width;
  private final int chunkSize;

  private Chunk[][] chunks;

  private Map<String, GamePlayer> players;
  private Map<String, Chunk> idsToChunks;
  private Map<DynamicEntity, Chunk> dynamicToChunk;

  public ChunkMap(int width, int height, int chunkSize) {
    this.height = height;
    this.width = width;
    this.chunkSize = chunkSize;
    init();
  }

  private void init() {
    chunks = new Chunk[height][width];
    players = new HashMap<String, GamePlayer>();
    idsToChunks = new HashMap<String, Chunk>();
    dynamicToChunk = new HashMap<DynamicEntity, Chunk>();
    initChunks();

  }

  private void initChunks() {

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        chunks[r][c] = new Chunk(r, c);

      }
    }
  }

  public void updateClients(Set<ClientState> clientStates) {

    Queue<Chunk> updated = new LinkedList<Chunk>();

    for (ClientState state : clientStates) {
      String clientId = state.getClientId();
      GamePlayer player = players.get(clientId);
      Chunk chunk = idsToChunks.get(clientId);
      updated.add(chunk);

      player.update(state);

    }
  }

  private void transfer(DynamicEntity dynamic, Chunk from, Chunk to) {
    from.removeEntity(dynamic);
    to.addEntity(dynamic);
  }

  @Override
  public void tick() {
    tickDynamic();
    // updateAllChunks();
  }

  private void tickDynamic() {
    Queue<Chunk> updated = new LinkedList<Chunk>();

    for (Entry<DynamicEntity, Chunk> entry : dynamicToChunk.entrySet()) {
      DynamicEntity dynamic = entry.getKey();
      Chunk chunk = entry.getValue();

      try {
        Chunk chunk2 = positionToChunk(dynamic);
        if (chunk != chunk2) {
          // The player moved to another chunk
          transfer(dynamic, chunk, chunk2);
          updated.add(chunk2);
        }
      } catch (IllegalChunkException e) {
        // The player went out of bounds
        dynamic.kill();

      }
    }
  }

  /**
   * Given a position on the screen, will return the chunk that the given row
   * and column can be found on.
   *
   * @param rPos
   *          The row position of the entity we are looking for. This must be a
   *          valid location on the map.
   * @param cPos
   *          The column position of the entity we are looking for. This must be
   *          a valid location on the map.
   * @return The Chunk where the given row and column should be found
   * @throws IllegalChunkException
   *           If the given position is not valid
   */
  private Chunk positionToChunk(int xPos, int yPos)
      throws IllegalChunkException {
    // TODO FIX
    int chunkRow = -1 * yPos / chunkSize;
    int chunkCol = xPos / chunkSize;

    try {
      return chunks[chunkRow][chunkCol];

    } catch (ArrayIndexOutOfBoundsException aioob) {
      throw new IllegalChunkException(xPos, yPos);
    }

  }

  private Chunk positionToChunk(Entity e) throws IllegalChunkException {
    int xPos = (int) e.getXPos();
    int yPos = (int) e.getYPos();
    return positionToChunk(xPos, yPos);
  }

  public void addEntity(Entity e) {
    Chunk c;
    try {
      c = positionToChunk(e);
      c.addEntity(e);
    } catch (IllegalChunkException e1) {
      // TODO log this, but nothing really needs to be done.

    }

  }

  /**
   * Calls the tick method on all chunks in the map.
   */
  private void updateAllChunks() {
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        chunks[r][c].tick();
      }
    }
  }

  class IllegalChunkException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -1057201821085086384L;

    public IllegalChunkException(int xPos, int yPos) {
      super("The given entity was not in bounds of the map. " + "xPos: " + xPos
          + " xPos: " + yPos);
    }
  }

}
