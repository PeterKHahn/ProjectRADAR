package edu.brown.cs.dreamteam.game;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.Entity;

public class Chunk implements Tickable {

  private Set<Entity> entities;

  private int row;
  private int col;

  public Chunk(int row, int col) {
    this.row = row;
    this.col = col;
    init();

  }

  private void init() {
    entities = new HashSet<Entity>();
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public void addEntity(Entity e) {
    entities.add(e);

  }

  public void removeEntity(Entity e) {
    entities.remove(e);
  }

  @Override
  public void tick() {
    for (Entity e : entities) {

    }

  }

}
