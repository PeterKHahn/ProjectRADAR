package edu.brown.cs.dreamteam.radar;

import edu.brown.cs.dreamteam.game.Inventory;
import edu.brown.cs.dreamteam.item.InventoryItem;

public class RadarPiece extends InventoryItem {

  @Override
  public void add(Inventory inventory) {
    inventory.addRadarPiece();

  }

}
