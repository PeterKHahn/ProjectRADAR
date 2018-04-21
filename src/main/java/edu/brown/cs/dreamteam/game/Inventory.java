package edu.brown.cs.dreamteam.game;

public class Inventory {
  private static final int MAX_GUNS = 3;
  private static final int MAX_BULLETS = 50;

  private int numBullets = 0;

  private int weaponIndex = 0;

  int numWeapons = 0;

  public Inventory() {
    init();
  }

  private void init() {
  }

  public boolean pickup(Item item) {

    return false;
  }

}
