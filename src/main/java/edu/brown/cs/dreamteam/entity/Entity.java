package edu.brown.cs.dreamteam.entity;

import java.util.Objects;

import edu.brown.cs.dreamteam.game.Tickable;

public abstract class Entity implements Tickable {

  private final String id;

  private int row;
  private int col;

  public Entity(String id, int row, int col) {
    this.id = id;
    this.row = row;
    this.col = col;
  }

  public String getId() {
    return id;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Entity) {
      Entity e = (Entity) o;
      return e.getId().equals(this.getId());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
