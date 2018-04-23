package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.weapon.DefaultWeapon;
import edu.brown.cs.dreamteam.weapon.Weapon;

public class Inventory {

  private Weapon weapon;

  public Inventory() {
    init();
  }

  private void init() {
    weapon = new DefaultWeapon();
  }

  public Weapon getActiveWeapon() {
    return weapon;
  }

  public void tick() {

  }

}
