package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.entity.Entity;

public abstract class Weapon extends Entity implements HitBoxed {

  public Weapon(String id) {
    super(id);

  }

  public abstract void fire();

  public abstract boolean canFire();

}
