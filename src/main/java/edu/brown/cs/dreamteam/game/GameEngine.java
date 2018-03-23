package edu.brown.cs.dreamteam.game;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.Entity;

public class GameEngine implements Runnable {

  private static final int FPS = 30;
  private static final int PRINT_RATE = 30;

  private static final int HEIGHT = 10;
  private static final int WIDTH = 10;
  private static final int CHUNK_SIZE = 100;

  private Set<Entity> entities;
  private ChunkMap chunks;

  private boolean running = false;
  private int ticks = 0;

  public GameEngine() {
    init();
  }

  private void init() {
    entities = new HashSet<Entity>();
    chunks = new ChunkMap(WIDTH, HEIGHT, CHUNK_SIZE);

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
      }

    }
  }

  private void tick() {
    chunks.tick();
  }

  private void addEntity(Entity e) {
    entities.add(e);
  }

  private void log() {
    System.out.println("Ticks: " + ticks);
  }
}
