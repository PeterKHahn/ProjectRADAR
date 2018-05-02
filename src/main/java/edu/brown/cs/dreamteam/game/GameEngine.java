package edu.brown.cs.dreamteam.game;

import java.util.Map;

import edu.brown.cs.dreamteam.ai.AiController;
import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventEmitter;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.main.Architect;

public class GameEngine implements Runnable {

  private static final int FPS = 20;
  private static final int PRINT_RATE = 3;

  private static final int HEIGHT = 1;
  private static final int WIDTH = 1;
  private static final int CHUNK_SIZE = 1000;

  private GameEventEmitter eventEmitter;
  private Architect architect;

  private ChunkMap chunks;
  private Board board; // Graph representation of the completed ChunkMap

  private boolean running = false;
  private int ticks = 0;

  /**
   * Creates a GameEngine given an Architect.
   *
   * @param architect
   *          The Architecture that the GameEngine is a part of
   */
  public GameEngine(Architect architect) {
    this.architect = architect;
    init(WIDTH, HEIGHT, CHUNK_SIZE);
  }

  /**
   * Constructor that allows specification of the ChunkMap's width, height, and
   * chunkSize.
   */
  public GameEngine(Architect architect, int width, int height, int chunkSize) {
    this.architect = architect;
    init(width, height, chunkSize);
  }

  private void init(int width, int height, int chunkSize) {
    chunks = new ChunkMap(width, height, chunkSize);
    eventEmitter = new GameEventEmitter();
    this.addGameEventListener(architect);
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
    Map<String, ClientState> updatedClientStates = architect
        .retrieveClientStates();
    chunks.updateClients(updatedClientStates);
    chunks.tick();
  }

  /**
   * Adds a player to the Game.
   *
   * @param p
   *          the player to add
   */
  public void addPlayer(GamePlayer p) {
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

  /**
   * Adds an AI player to the game, assuming that the game board has already
   * been initialized by calling makeBoard().
   *
   * @param id
   *          The ID of the AI player.
   */
  public void addAiPlayer(int id) {
    AiController controller = new AiController(Integer.toString(id), board);
    chunks.addDynamic(controller.getPlayer());
  }

  /**
   * Initializes the Board representation of the GameMap for AI players to use.
   */
  public void board() {
    board = new Board(chunks);
  }

  public Board getBoard() {
    return board;
  }

  private void log() {
    // System.out.println("Ticks: " + ticks);
  }
}
