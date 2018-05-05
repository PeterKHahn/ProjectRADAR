package edu.brown.cs.dreamteam.item;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.radar.RadarPiece;
import edu.brown.cs.dreamteam.weapon.ClapWeapon;
import edu.brown.cs.dreamteam.weapon.DrStrangeWeapon;
import edu.brown.cs.dreamteam.weapon.EnergyBlast;

public class Item extends Entity {

  private Type type;
  private InventoryItem item;

  private Vector center;

  public Item(String id, Vector center, Type type, InventoryItem item) {
    super(id);
    this.center = center;
    this.type = type;
    this.item = item;
  }

  @Override
  public void tick(ChunkMap chunkMap) {

  }

  @Override
  public Vector center() {
    return center;
  }

  public InventoryItem getItem() {
    return item;
  }

  public Type getType() {
    return type;
  }

  public static Item DrStrangeWeapon(String id, Vector center) {
    return new Item(id, center, Type.WEAPON, new DrStrangeWeapon());
  }

  public static Item EnergyBlast(String id, Vector center) {
    return new Item(id, center, Type.WEAPON, new EnergyBlast());
  }

  public static Item ClapWeapon(String id, Vector center) {
    return new Item(id, center, Type.WEAPON, new ClapWeapon());
  }

  public static Item random(String id, Vector center) {

    int rand = (int) (Math.random() * 4);
    switch (rand) {
      case 0:
        return Item.DrStrangeWeapon(id, center);
      case 1:
        return new Item(id, center, Type.RADAR_PIECE, new RadarPiece());
      case 2:
        return Item.ClapWeapon(id, center);
      case 3:
        return Item.EnergyBlast(id, center);
      case 4:
        return new Item(id, center, Type.RADAR_PIECE, new RadarPiece());
      default:
        return new Item(id, center, Type.RADAR_PIECE, new RadarPiece());
    }

  }

}
