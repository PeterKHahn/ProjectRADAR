package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.weapon.Gun;

public class Inventory {
  private static final int MAX_GUNS = 3;
  private static final int MAX_BULLETS = 50;

  private Gun[] weapons;
  private int numBullets = 0;

  private int weaponIndex = 0;

  int numWeapons = 0;

  public Inventory() {
    init();
  }

  private void init() {
    weapons = new Gun[MAX_GUNS];
  }

  public boolean pickup(Item item) {
    if (numWeapons < MAX_GUNS) {
      for (int i = 0; i < MAX_GUNS; i++) {
        if (weapons[i] == null) {
          weapons[i] = null;
          numWeapons++;
          return true;
        }
      }
    }
    return false;
  }

}
