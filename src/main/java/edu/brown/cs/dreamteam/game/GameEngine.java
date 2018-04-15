package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.ai.AiController;
import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventEmitter;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.main.Architect;
import java.util.List;
import java.util.Map;

public class GameEngine implements Runnable {

  private static final int FPS = 30;
  private static final int PRINT_RATE = 3;

  private static final int HEIGHT = 5;
  private static final int WIDTH = 5;
  private static final int CHUNK_SIZE = 3;
  private static final int NUM_PLAYERS = 4;

  private GameEventEmitter eventEmitter;
  private Architect architect;

  private ChunkMap chunks;
  private Board board; // Graph representation of the completed ChunkMap

  private boolean running = false;
  private int ticks = 0;

  private List<AiController> aiControllers;

  /**
   * Creates a GameEngine given an Architect
   * 
   * @param architect
   *          The Architecture that the GameEngine is a part of
   */
  public GameEngine(Architect architect) {
    this.architect = architect;

    init();
  }

  private void init() {
    chunks = new ChunkMap(WIDTH, HEIGHT, CHUNK_SIZE);
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
   * Adds a listener to the game engine's GameEventEmitter
   * 
   * @param listener
   */
  public void addGameEventListener(GameEventListener listener) {
    eventEmitter.addGameEventListener(listener);
  }

  /**
   * Represents a change in game event, by updating internal states
   */
  private void tick() {
    Map<String, ClientState> updatedClientStates = architect
        .retrieveClientStates();
    chunks.updateClients(updatedClientStates);
    tickAi();
    chunks.tick();
  }

  private void tickAi() {
    int numAi = aiControllers.size();
    for (int i = 0; i < numAi; i++) {
      aiControllers.get(i).makeNextMove(chunks);
    }
  }

  /**
   * Adds a player to the Game
   * 
   * @param p
   *          the player to add
   */
  public void addPlayer(GamePlayer p) {
    chunks.addPlayer(p);

  }

  public void addObstacle(Obstacle ob) {
    chunks.addObstacle(ob);
  }

  public void addAiPlayer(int id) {
    aiControllers.add(new AiController(Integer.toString(id), board));
    chunks.addPlayer(aiControllers.get(aiControllers.size() - 1).getPlayer());
  }

  public void makeBoard() {
    board = new Board(chunks);
  }

  private void log() {
    // System.out.println("Ticks: " + ticks);
  }
}
