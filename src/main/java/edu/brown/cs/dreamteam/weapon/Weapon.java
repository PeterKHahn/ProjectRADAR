package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;

public abstract class Weapon {

  public Weapon() {

  }

  public abstract void fire();

  public abstract boolean canFire();

  public abstract boolean isActive();

  public abstract BoxSet hitBox();

}
