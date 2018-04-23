package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public class EnergyBlast extends Weapon {

  private double radius = 5;
  private BoxSet hitBox;

  private int duration = 10;
  private int cooldown = 30;

  private int nextFire = 0; // Time since we last fired
  private int timeActive = 0;

  private boolean active;

  private Vector hitBoxOffset;

  public EnergyBlast() {
    init();
  }

  private void init() {
    hitBoxOffset = new Vector(0, 0);
    hitBox = new BoxSet(new Box(radius));
  }

  @Override
  public void tick() {
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

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public BoxSet hitBox() {
    return hitBox;
  }

  @Override
  public Vector hitBoxOffset() {
    return hitBoxOffset;
  }

}
