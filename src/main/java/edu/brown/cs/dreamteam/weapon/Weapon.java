package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.HitBoxed;

public abstract class Weapon implements HitBoxed {

  public Weapon() {

  }

  public abstract void fire();

  public abstract boolean canFire();

  public abstract void tick();

}
