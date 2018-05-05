package edu.brown.cs.dreamteam.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;

public class ItemGameMap implements GameMap {

  private static final int HEIGHT = 2;
  private static final int WIDTH = 4;
  private static final int CHUNK_SIZE = 50;

  @Override
  public Collection<Obstacle> getObstacles() {
    return Collections.emptyList();
  }

  @Override
  public Collection<Item> getItems() {
    List<Item> ls = new ArrayList<Item>();
    ls.add(Item.EnergyBlast("a", new Vector(50, 50)));
    ls.add(Item.ClapWeapon("b", new Vector(75, 50)));
    ls.add(Item.DrStrangeWeapon("c", new Vector(100, 50)));

    return ls;
  }

  @Override
  public KeyItem getKeyItem() {
    return new KeyItem("key", new Vector(125, 50));
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getChunkSize() {
    return CHUNK_SIZE;
  }

  @Override
  public int numPlayers() {
    return 1;
  }

  @Override
  public int numDummyPlayers() {
    return 1;
  }

}
