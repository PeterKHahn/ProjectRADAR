package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.game.Inventory;
import edu.brown.cs.dreamteam.item.InventoryItem;

public abstract class Weapon extends InventoryItem implements HitBoxed {
  protected Attack attack;

  public void fire() {
    if (canStartAttack()) {
      attack.attack();
    }
  }

  public boolean canStartAttack() {
    return attack.canStartAttack();

  }

  public void tick() {
    attack.tick();

  }

  @Override
  public double baseDamage() {
    return attack.baseDamage();
  }

  @Override
  public BoxSet hitBox() {
    return attack.hitBox();
  }

  @Override
  public void add(Inventory inventory) {
    inventory.addWeapon(this);
  }

}
