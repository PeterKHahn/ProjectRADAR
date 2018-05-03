package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Interactable;
import edu.brown.cs.dreamteam.entity.Marker;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.item.Item;

/**
 * Chunk Map is the primary location of our storage of entity information,
 * including which areas they affect.
 * 
 * @author peter
 *
 */
public class ChunkMap {

  private final int height;
  private final int width;
  private final int chunkSize;

  public final int totalWidth;
  public final int totalHeight;

  private Chunk[][] chunks;
  private Board board; // Graph representation of the completed ChunkMap

  private Set<Marker> markers;

  private Map<String, GamePlayer> players;
  private Map<String, DynamicEntity> dynamic;

  private Set<StaticEntity> staticEntities;

  private int tickCount;

  /**
   * Constructor for ChunkMap.
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

  public void makeBoard() {
    board = new Board(this);
  }

  public Board getBoard() {
    return board;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getTotalWidth() {
    return totalWidth;
  }

  public int getTotalHeight() {
    return totalHeight;
  }

  private void init() {
    chunks = new Chunk[height][width];
    dynamic = new HashMap<String, DynamicEntity>();
    players = new HashMap<String, GamePlayer>();
    staticEntities = new HashSet<StaticEntity>();
    markers = new HashSet<Marker>();
    initChunks();

  }

  /**
   * Initializes each chunk.
   */
  private void initChunks() {

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        chunks[r][c] = new Chunk(r, c);

      }
    }
  }

  public int getChunkSize() {
    return chunkSize;
  }

  /**
   * Given clientStates, updates each client to fit the client state.
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

  /**
   * Ticks all entities in the Chunk Map.
   */
  public void tick() {
    for (DynamicEntity e : dynamic.values()) {
      if (e.alive()) {
        e.tick(this);
      }
    }
    for (DynamicEntity e : dynamic.values()) {
      if (!inBounds(e)) {
        e.kill();
      }
    }
    tickCount++;
  }

  public boolean inBounds(Entity e) {
    Vector center = e.center();
    return (center.x >= 0 && center.x <= getTotalWidth() && center.y >= 0
        && center.y <= getTotalHeight());

  }

  public int getChunkRow(double ypos) {
    int rpos = (totalHeight - (int) ypos - 1);
    return rpos / chunkSize;
  }

  public int getChunkCol(double xpos) {
    int cpos = (int) xpos;
    return cpos / chunkSize;
  }

  /**
   * Adds a player to the game.
   * 
   * @param player
   *          the game player to be added
   */
  public void addPlayer(GamePlayer player) {
    players.put(player.getId(), player);
    dynamic.put(player.getId(), player);

  }

  public void addDynamic(DynamicEntity e) {
    Collection<Chunk> chunks = chunksInRange(e);
    for (Chunk c : chunks) {
      c.addDynamic(e);
    }
    dynamic.put(e.getId(), e);
  }

  public void addStatic(StaticEntity e) {
    Collection<Chunk> chunks = chunksInRange(e);
    for (Chunk c : chunks) {
      c.addStatic(e);
    }
    staticEntities.add(e);
  }

  public void addItem(Item item) {
    Chunk c = chunkFromPosition(item.center());
    c.addItem(item);
  }

  public void removeItem(Item item) {
    Chunk c = chunkFromPosition(item.center());
    c.removeItem(item);
    board.removePosition(new Position(item.center().x, item.center().y));
  }

  public Collection<Chunk> chunksInRange(Interactable e) {
    return chunksInRange(e, e.reach());

  }

  public void addMarker(Marker marker) {
    markers.add(marker);
  }

  public Set<Marker> markers() {
    return markers;
  }

  public Collection<Chunk> chunksInRange(Entity e, double radius) {
    double left = e.center().x - radius;
    double right = e.center().x + radius;
    double top = e.center().y + radius;
    double bottom = e.center().y - radius;

    int fromRow = getChunkRow(top);
    int toRow = getChunkRow(bottom);
    int fromCol = getChunkCol(left);
    int toCol = getChunkCol(right);

    return chunksInRange(fromRow, toRow, fromCol, toCol);
  }

  /**
   * Returns the chunks in the range of the bounds.
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
  public Collection<Chunk> chunksInRange(int fromRow, int toRow, int fromCol,
      int toCol) {
    Collection<Chunk> res = new LinkedList<Chunk>();
    for (int r = Math.max(fromRow, 0); r <= toRow && r < height; r++) {
      for (int c = Math.max(fromCol, 0); c <= toCol && c < width; c++) {
        res.add(chunks[r][c]);
      }
    }

    return res;

  }

  public Chunk chunkFromPosition(Vector position) {
    int row = getChunkRow(position.y);
    int col = getChunkCol(position.x);

    return chunks[row][col];
  }

  public Set<Interactable> interactableFromChunks(Collection<Chunk> chunks) {
    Set<Interactable> res = new HashSet<Interactable>();
    for (Chunk c : chunks) {
      res.addAll(c.getInteractable());
    }

    return res;
  }

  public static Set<DynamicEntity> dynamicFromChunks(Collection<Chunk> chunks) {
    Set<DynamicEntity> res = new HashSet<DynamicEntity>();
    for (Chunk c : chunks) {
      res.addAll(c.getDynamic());
    }

    return res;
  }

  public static Set<StaticEntity> staticFromChunks(Collection<Chunk> chunks) {
    Set<StaticEntity> res = new HashSet<StaticEntity>();
    for (Chunk c : chunks) {
      res.addAll(c.getStatic());
    }

    return res;
  }

  public static Set<Item> itemsFromChunks(Collection<Chunk> chunks) {
    Set<Item> res = new HashSet<>();
    for (Chunk c : chunks) {
      res.addAll(c.getItems());
    }
    return res;
  }

  public Collection<GamePlayer> getPlayers() {
    return players.values();
  }

  public Set<StaticEntity> getStaticEntities() {
    return Collections.unmodifiableSet(staticEntities);
  }

  public int tickCount() {
    return tickCount;
  }

}
