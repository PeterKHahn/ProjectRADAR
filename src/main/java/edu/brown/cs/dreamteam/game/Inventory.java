package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.weapon.Weapon;

public class Inventory {

  private Weapon weapon;

  public Inventory() {
    init();
  }

  private void init() {
  }

  public Weapon getActiveWeapon() {
    return weapon;
  }

  public boolean hasActiveWeapon() {
    return weapon != null;
  }

}
