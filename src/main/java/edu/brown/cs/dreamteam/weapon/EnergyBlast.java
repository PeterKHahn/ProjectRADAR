package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.Point;
import edu.brown.cs.dreamteam.game.ChunkMap;

public class EnergyBlast extends Weapon {

  private double radius;
  private BoxSet hitBox;

  private int duration = 10;
  private int cooldown = 30;

  private int nextFire = 0; // Time since we last fired
  private int timeActive = 0;

  private boolean active;

  public EnergyBlast(String id) {
    super(id);
  }

  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public BoxSet hitBox() {
    return hitBox;
  }

  @Override
  public double reach() {
    return radius;
  }

  @Override
  public Point center() {
    return hitBox.center();
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    nextFire = Math.max(0, nextFire--);
    if (active) {
      timeActive++;
      if (timeActive > duration) {
        active = false;
        timeActive = 0;
      }
    }
  }

  @Override
  public void fire() {
    if (!canFire()) {
      return;
    }
    nextFire = cooldown;
    active = true;

  }

  @Override
  public boolean canFire() {
    return nextFire == 0;
  }

}
