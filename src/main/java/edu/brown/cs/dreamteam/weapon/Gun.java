package edu.brown.cs.dreamteam.weapon;

import java.util.Collection;

import edu.brown.cs.dreamteam.entity.Bullet;
import edu.brown.cs.dreamteam.entity.Entity;

public abstract class Gun extends Entity {

  private double speed;

  public Gun(String id, double speed) {
    super(id);
    this.speed = speed;
  }

  public abstract Collection<Bullet> fire(double x, double y, double theta);

  public abstract boolean canFire();

}
