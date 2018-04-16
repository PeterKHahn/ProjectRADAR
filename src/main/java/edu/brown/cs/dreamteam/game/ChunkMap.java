package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import edu.brown.cs.dreamteam.box.Boxed;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.event.ClientState;

/**
 * Chunk Map is the primary location of our storage of entity information,
 * including which areas they affect
 * 
 * @author peter
 *
 */
public class ChunkMap {

  private final int height;
  private final int width;
  private final int chunkSize;

  private final int totalWidth;
  private final int totalHeight;

  private Chunk[][] chunks;

  private Map<String, GamePlayer> players;
  private Multimap<GamePlayer, Chunk> playerToChunks;
  private Map<String, Obstacle> obstacles;
  private Multimap<Obstacle, Chunk> obstacleToChunks;

  /**
   * Constructor for ChunkMap
   * 
   * @param width
   *          The number of chunks wide
   * @param height
   *          the number of chunks high
   * @param chunkSize
   *          the size of each chunk
   */
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
    playerToChunks = HashMultimap.create();
    obstacles = new HashMap<String, Obstacle>();
    obstacleToChunks = HashMultimap.create();
    initChunks();

  }

  /**
   * Initializes each chunk
   */
  private void initChunks() {

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        chunks[r][c] = new Chunk(r, c);

      }
    }
  }

  /**
   * Given clientStates, updates each client to fit the client state
   * 
   * @param clientStates
   *          A Map of ID to client state that holds information about the state
   *          of all clients
   */
  public void updateClients(Map<String, ClientState> clientStates) {

    for (Entry<String, ClientState> entry : clientStates.entrySet()) {
      String clientId = entry.getKey();
      GamePlayer player = players.get(clientId);

      player.update(entry.getValue());

    }
  }

  public void tick() {
    tickPlayer();
  }

  /**
   * Ticks all dynamic entities in the game, by updating their position based on
   * their velocity
   */
  private void tickPlayer() {

    for (GamePlayer player : playerToChunks.keySet()) {

      moveDynamic(player);

      Collection<Chunk> newChunks = chunksInRange(player);

      playerToChunks.removeAll(player);
      playerToChunks.putAll(player, newChunks);

    }
  }

  /**
   * A way to move a given dynamic, handling collisions in linear time to the
   * velocity of the dynamic. The dynamic entity must not be colliding any
   * static Entity when first passed into this function, and it will not collide
   * with any static entity upon exiting this function. This method does not
   * function at high velocities. Corners can be cut, and thin objects can be
   * passed through, if the velocity of a given dynamic entity is high enough.
   * If this proves to be a problem, we will need to consider a new way of
   * approaching this task.
   *
   * @param dynamicE
   */
  private void moveDynamic(DynamicEntity dynamicE) {

    Collection<Chunk> chunks = chunksInRange(dynamicE);
    Set<StaticEntity> staticInRange = staticFromChunks(chunks);

    dynamicE.updateX();

    int velocityCoeff = dynamicE.getXVelocity() > 0 ? 1 : -1;

    for (StaticEntity s : staticInRange) {
      while (dynamicE.collidesWith(s)) {
        dynamicE.setXPos((int) dynamicE.getXPos() - velocityCoeff);
      }
    }

    dynamicE.updateY();

    for (StaticEntity s : staticInRange) {
      while (dynamicE.collidesWith(s)) {
        dynamicE.setYPos((int) dynamicE.getYPos() - velocityCoeff);
      }
    }

  }

  private int getChunkRow(double yPos) {
    int rPos = (totalHeight - (int) yPos - 1);
    return rPos / chunkSize;
  }

  private int getChunkCol(double xPos) {
    int cPos = (int) xPos;
    return cPos / chunkSize;
  }

  /**
   * Adds a player to the game
   * 
   * @param e
   *          the game player to be added
   */
  public void addPlayer(GamePlayer e) {

    Collection<Chunk> chunks = chunksInRange(e);
    for (Chunk c : chunks) {
      c.addDynamic(e);
    }

    playerToChunks.putAll(e, chunks);
    players.put(e.getId(), e);

  }

  public void addObstacle(Obstacle e) {
    Collection<Chunk> chunks = chunksInRange(e);
    for (Chunk c : chunks) {
      c.addStatic(e);
    }
    obstacleToChunks.putAll(e, chunks);
    obstacles.put(e.getId(), e);

  }

  private Collection<Chunk> chunksInRange(Boxed e) {
    int fromRow = getChunkRow(e.getUpper());
    int toRow = getChunkRow(e.getLower());
    int fromCol = getChunkCol(e.getLeft());
    int toCol = getChunkCol(e.getRight());

    return chunksInRange(fromRow, toRow, fromCol, toCol);

  }

  /**
   * Returns the chunks in the range of the bounds
   * 
   * @param fromRow
   *          the row to start
   * @param toRow
   *          the row to end
   * @param fromCol
   *          the col to start
   * @param toCol
   *          the col to end
   * @return A collection of Chunks within the range of the bounds
   */
  private Collection<Chunk> chunksInRange(int fromRow, int toRow, int fromCol,
      int toCol) {
    Collection<Chunk> res = new LinkedList<Chunk>();
    for (int r = Math.max(fromRow, 0); r <= toRow && r < height; r++) {
      for (int c = Math.max(fromCol, 0); c <= toCol && c < width; c++) {
        res.add(chunks[r][c]);
      }
    }

    return res;

  }

  /**
   * Returns the set of staticEntities within the chunks
   * 
   * @param chunks
   *          the Collection of chunks to retrieve static entities from
   * @return A Set of Static Entities contined in chunks
   */
  private Set<StaticEntity> staticFromChunks(Collection<Chunk> chunks) {
    Set<StaticEntity> res = new HashSet<StaticEntity>();
    for (Chunk c : chunks) {
      res.addAll(c.getStaticEntities());
    }
    return res;
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
