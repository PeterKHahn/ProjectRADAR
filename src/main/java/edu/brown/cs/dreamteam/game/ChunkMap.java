package edu.brown.cs.dreamteam.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.utility.Logger;

public class ChunkMap implements Tickable {

  private final int height;
  private final int width;
  private final int chunkSize;

  private final int totalWidth;
  private final int totalHeight;

  private Chunk[][] chunks;

  private Map<String, GamePlayer> players;
  private Map<String, Chunk> idsToChunks;
  private Map<DynamicEntity, Chunk> dynamicToChunk;

  public ChunkMap(int width, int height, int chunkSize) {
    this.height = height;
    this.width = width;
    this.chunkSize = chunkSize;
    totalWidth = width * chunkSize;
    totalHeight = height * chunkSize;
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

  public void updateClients(Map<String, ClientState> clientStates) {

    Queue<Chunk> updated = new LinkedList<Chunk>();

    for (Entry<String, ClientState> entry : clientStates.entrySet()) {
      String clientId = entry.getKey();
      GamePlayer player = players.get(clientId);
      Chunk chunk = idsToChunks.get(clientId);
      updated.add(chunk);

      player.update(entry.getValue());

    }
  }

  private void transfer(DynamicEntity dynamic, Chunk from, Chunk to) {
    from.removeDynamic(dynamic);
    to.addDynamic(dynamic);
    dynamicToChunk.put(dynamic, to);
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
      dynamic.tick(); // moves the dynamic

      try {
        Chunk chunk2 = positionToChunk(dynamic);
        if (chunk != chunk2) {
          // The player moved to another chunk
          Logger.logDebug("Player has been transfered");
          transfer(dynamic, chunk, chunk2);
          updated.add(chunk2);
        }
      } catch (IllegalChunkException e) {
        // The player went out of bounds
        Logger.logDebug("The player has gone out of bounds");
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
  private Chunk positionToChunk(double xPos, double yPos)
      throws IllegalChunkException {

    if (!inBounds(xPos, yPos)) {
      throw new IllegalChunkException(xPos, yPos);
    }
    int rPos = totalHeight - (int) yPos - 1;
    int cPos = (int) xPos;

    int chunkRow = rPos / chunkSize;
    int chunkCol = cPos / chunkSize;

    try {
      return chunks[chunkRow][chunkCol];

    } catch (ArrayIndexOutOfBoundsException aioob) {
      throw new IllegalChunkException(xPos, yPos);
    }

  }

  private boolean inBounds(double xPos, double yPos) {
    return xPos >= 0 && yPos >= 0 && xPos < totalWidth && yPos < totalHeight;
  }

  private Chunk positionToChunk(Entity e) throws IllegalChunkException {

    return positionToChunk(e.getXPos(), e.getYPos());
  }

  public void addPlayer(GamePlayer e) {
    // TODO abstract this out more.
    Chunk c;
    try {
      Logger.logDebug("Game Player: " + e);

      c = positionToChunk(e);
      c.addDynamic(e);
      dynamicToChunk.put(e, c);
      players.put(e.getId(), e);
    } catch (IllegalChunkException e1) {
      // TODO log this, but nothing really needs to be done.
      Logger.logDebug("Illegal position");

    }

  }

  public Chunk[][] getChunkArray() {
    return chunks;
  }

  class IllegalChunkException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -1057201821085086384L;

    public IllegalChunkException(double xPos, double yPos) {
      super("The given entity was not in bounds of the map. " + "xPos: " + xPos
          + " xPos: " + yPos);
    }
  }

}
