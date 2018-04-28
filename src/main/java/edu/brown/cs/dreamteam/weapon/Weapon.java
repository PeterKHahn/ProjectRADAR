package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.item.InventoryItem;

public abstract class Weapon extends InventoryItem implements HitBoxed {

  public abstract void fire();

  public abstract boolean canFire();

  public abstract void tick();

}
