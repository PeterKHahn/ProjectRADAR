package edu.brown.cs.dreamteam.game;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventEmitter;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.main.Architect;

public class GameEngine implements Runnable {

  private static final int FPS = 30;
  private static final int PRINT_RATE = 30;

  private static final int HEIGHT = 10;
  private static final int WIDTH = 10;
  private static final int CHUNK_SIZE = 100;

  private GameEventEmitter eventEmitter;
  private Architect architect;

  private Set<Entity> entities;
  private ChunkMap chunks;

  private boolean running = false;
  private int ticks = 0;

  public GameEngine(Architect architect) {
    init();
    this.architect = architect;
  }

  private void init() {
    entities = new HashSet<Entity>();
    chunks = new ChunkMap(WIDTH, HEIGHT, CHUNK_SIZE);
    eventEmitter = new GameEventEmitter();

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

  public void addGameEventListener(GameEventListener listener) {
    eventEmitter.addGameEventListener(listener);
  }

  private void tick() {
    Set<ClientState> updatedClientStates = architect.retrieveClientStates();
    chunks.updateClients(updatedClientStates);
    chunks.tick();
  }

  private void addEntity(Entity e) {
    entities.add(e);
  }

  public void addPlayer(GamePlayer p) {
    addEntity(p);
  }

  private void log() {
    System.out.println("Ticks: " + ticks);
  }
}
