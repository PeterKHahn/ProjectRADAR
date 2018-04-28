package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public abstract class AttackFrame {

  private final int duration;
  private final double damage;
  private final boolean active;

  private final BoxSet hitbox;

  private final Vector vector;

  public AttackFrame(int duration, double damage, boolean active, BoxSet hitbox,
      Vector vector) {
    this.duration = duration;
    this.damage = damage;
    this.active = active;
    this.hitbox = hitbox;
    this.vector = vector;

  }

  public double duration() {
    return duration;
  }

  public double damage() {
    return damage;
  }

  public boolean active() {
    return active;
  }

  public BoxSet hitbox() {
    return hitbox;
  }

  public Vector vector() {
    return vector;
  }

}
