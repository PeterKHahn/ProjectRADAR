package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.entity.Entity;

public class ChunkMap implements Tickable {

  private final int height;
  private final int width;
  private final int chunkSize;

  private Chunk[][] chunks;

  public ChunkMap(int width, int height, int chunkSize) {
    this.height = height;
    this.width = width;
    this.chunkSize = chunkSize;
    chunks = new Chunk[height][width];

    initChunks();
  }

  private void initChunks() {

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        chunks[r][c] = new Chunk(r, c);

      }
    }
  }

  @Override
  public void tick() {
    updateAllChunks();
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
  private Chunk positionToChunk(int rPos, int cPos)
      throws IllegalChunkException {
    int chunkRow = rPos / chunkSize;
    int chunkCol = cPos / chunkSize;

    try {
      return chunks[chunkRow][chunkCol];

    } catch (ArrayIndexOutOfBoundsException aioob) {
      throw new IllegalChunkException(rPos, cPos);
    }

  }

  private Chunk positionToChunk(Entity e) throws IllegalChunkException {
    int rPos = e.getRow();
    int cPos = e.getCol();
    return positionToChunk(rPos, cPos);
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

    public IllegalChunkException(int rPos, int cPos) {
      super("The given entity was not in bounds of the map. " + "Row: " + rPos
          + " Col: " + cPos);
    }
  }

}
