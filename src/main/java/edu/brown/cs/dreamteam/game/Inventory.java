package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.item.InventoryItem;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.Type;
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
    weapon.tick();
  }

  private void addWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  private void addRadarPiece() {

  }

  public void addItem(Item item) {
    Type type = item.getType();
    InventoryItem toAdd = item.getItem();
    switch (type) {
      case WEAPON:
        Weapon weapon = (Weapon) toAdd;
        addWeapon(weapon);
        break;
      case RADAR_PIECE:
        break;
      default:
        break;
    }

  }

}
