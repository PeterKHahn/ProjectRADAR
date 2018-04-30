package edu.brown.cs.dreamteam.item;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.game.ChunkMap;

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
  public double reach() {
    return 0;
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

}
