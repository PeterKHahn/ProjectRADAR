package edu.brown.cs.dreamteam.game;

import java.util.Map;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventEmitter;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.main.Architect;
import edu.brown.cs.dreamteam.utility.Logger;

public class GameEngine implements Runnable {

  private static final int FPS = 5;
  private static final int PRINT_RATE = 3;

  private static final int HEIGHT = 1;
  private static final int WIDTH = 1;
  private static final int CHUNK_SIZE = 1000;

  private GameEventEmitter eventEmitter;
  private Architect architect;
  private EntityFactory entityFactory;

  private ChunkMap chunks;

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
    init();

  }

  private void init() {
    chunks = new ChunkMap(WIDTH, HEIGHT, CHUNK_SIZE);
    eventEmitter = new GameEventEmitter();
    entityFactory = new EntityFactory(chunks);
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
    Logger.logMessage("Added game player: " + p.getId());
    entityFactory.addPlayer(p);

  }

  public void addObstacle(Obstacle ob) {
    entityFactory.addObstacle(ob);
  }

  public void addItem(Item item) {
    entityFactory.addItem(item);
  }

  public void addAiPlayer() {

  }

  private void log() {
    // System.out.println("Ticks: " + ticks);
  }
}
