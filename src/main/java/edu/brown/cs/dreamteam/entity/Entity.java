package edu.brown.cs.dreamteam.entity;

import java.util.Objects;

public abstract class Entity {

  private final String id;

  private double x;
  private double y;

  public Entity(String id, double x, double y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }

  public String getId() {
    return id;
  }

  public double getXPos() {
    return x;
  }

  public double getYPos() {
    return y;
  }

  public void setXPos(double x) {
    this.x = x;
  }

  public void setYPos(double y) {
    this.y = y;
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

  public abstract void kill();

}
