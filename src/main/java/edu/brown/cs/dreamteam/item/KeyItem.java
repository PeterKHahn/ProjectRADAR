package edu.brown.cs.dreamteam.item;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.weapon.DrStrangeWeapon;

public class KeyItem extends Item {

  public KeyItem(String id, Vector center) {
    super(id, center, Type.WEAPON, new DrStrangeWeapon());
    // TODO Auto-generated constructor stub
  }

}
