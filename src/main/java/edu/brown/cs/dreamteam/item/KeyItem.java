package edu.brown.cs.dreamteam.item;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.weapon.BrokenWeapon;

public class KeyItem extends Item {

  public KeyItem(String id, Vector center) {
    super(id, center, Type.WEAPON, new BrokenWeapon());
  }

}
