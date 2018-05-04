package edu.brown.cs.dreamteam.game;

import java.util.Map;

import edu.brown.cs.dreamteam.ai.AiController;
import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Marker;
import edu.brown.cs.dreamteam.entity.Playable;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventEmitter;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;
import edu.brown.cs.dreamteam.main.Room;

public class GameEngine implements Runnable {

  private static final int FPS = 30;
  private static final int PRINT_RATE = 3;

  private final int HEIGHT;
  private final int WIDTH;
  private final int CHUNK_SIZE;

  public final Vector CENTER;

  private GameEventEmitter eventEmitter;
  private Room room;
  private ChunkMap chunks;

  private boolean running = false;
  private int ticks = 0;

  /**
   * Creates a GameEngine given an Architect.
   *
   * @param architect
   *          The Architecture that the GameEngine is a part of
   */
  public GameEngine(int height, int width, int chunkSize, Room r) {
    this.HEIGHT = height;
    this.WIDTH = width;
    this.CHUNK_SIZE = chunkSize;
    this.room = r;
    CENTER = new Vector(WIDTH * CHUNK_SIZE / 2, HEIGHT * CHUNK_SIZE / 2);
    init(WIDTH, HEIGHT, CHUNK_SIZE);
  }

  private void init(int width, int height, int chunkSize) {
    chunks = new ChunkMap(width, height, chunkSize);
    eventEmitter = new GameEventEmitter();
    this.addGameEventListener(room);
  }

  @Override
  public void run() {
    running = true;
    long lastTime = System.nanoTime();
    double nsPerTick = 1000000000.0 / FPS;
    double delta = 0;

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / nsPerTick;
      lastTime = now;
      while (delta >= 1) {
        ticks++;
        tick();
        if (ticks % PRINT_RATE == 0) {
          log();
        }
        delta -= 1.0;
        eventEmitter.emit(chunks);
      }

    }
  }

  /**
   * Adds a listener to the game engine's GameEventEmitter.
   *
   * @param listener
   *          the listener to add
   */
  public void addGameEventListener(GameEventListener listener) {
    eventEmitter.addGameEventListener(listener);
  }

  /**
   * Represents a change in game event, by updating internal states.
   */
  private void tick() {
    Map<String, ClientState> updatedClientStates = room.retrieveClientStates();

    chunks.updateClients(updatedClientStates);
    chunks.tick();
  }

  /**
   * Adds a player to the Game.
   *
   * @param p
   *          the player to add
   */
  public void addPlayer(Playable p) {
    chunks.addPlayer(p);

  }

  public void addStatic(StaticEntity e) {
    chunks.addStatic(e);
  }

  public void addDynamic(DynamicEntity e) {
    chunks.addDynamic(e);
  }

  public void addItem(Item item) {
    chunks.addItem(item);
  }

  public void addKeyItem(KeyItem item) {
    chunks.addKeyItem(item);

  }

  public void addMarker(Marker marker) {
    chunks.addMarker(marker);
  }

  /**
   * Adds an AI player to the game, assuming that the game board has already
   * been initialized by calling makeBoard().
   *
   * @param id
   *          The ID of the AI player.
   */
  public void addAiPlayers(int numHumans) {
    for (int i = 1; i < 5 - numHumans; i++) {
      AiController controller = new AiController(Integer.toString(i),
          chunks.getBoard());
      chunks.addDynamic(controller.getPlayer());
      chunks.addPlayer(controller.getPlayer());
    }

  }

  /**
   * Initializes the Board representation of the GameMap for AI players to use.
   */
  public void board() {
    chunks.makeBoard();
  }

  public Board getBoard() {
    return chunks.getBoard();
  }

  private void log() {
    // System.out.println("Ticks: " + ticks);
  }
}
