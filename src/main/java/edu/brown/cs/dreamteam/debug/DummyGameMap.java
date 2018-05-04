package edu.brown.cs.dreamteam.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;
import edu.brown.cs.dreamteam.item.Type;
import edu.brown.cs.dreamteam.radar.RadarPiece;
import edu.brown.cs.dreamteam.weapon.ClapWeapon;
import edu.brown.cs.dreamteam.weapon.DrStrangeWeapon;
import edu.brown.cs.dreamteam.weapon.EnergyBlast;

// note: radius of obstacles hs to be at least 15 to be valid,
// unless we want the pick-up items to be literal specks
public class DummyGameMap implements GameMap {

  private static final int HEIGHT = 6;
  private static final int WIDTH = 6;
  private static final int CHUNK_SIZE = 50;

  @Override
  public Collection<Obstacle> getObstacles() {

    Obstacle x = new Obstacle("x", new Vector(50, 15), 10);
    Obstacle y = new Obstacle("y", new Vector(50, 50), 10);
    Obstacle z = new Obstacle("z", new Vector(50, 85), 10);
    Obstacle off = new Obstacle("off", new Vector(0, 0), 50);

    List<Obstacle> ls = new ArrayList<>();
    ls.add(x);
    // ls.add(y);
    ls.add(z);
    ls.add(off);
    return ls;
  }

  @Override
  public Collection<Item> getItems() {
    Item a = new Item("Item1", new Vector(73, 160), Type.WEAPON,
        new EnergyBlast());
    Item b = new Item("Item2", new Vector(73, 180), Type.WEAPON,
        new ClapWeapon());
    Item c = new Item("Item3", new Vector(103, 160), Type.WEAPON,
        new DrStrangeWeapon());
    Item d = new Item("Item4", new Vector(103, 180), Type.RADAR_PIECE,
        new RadarPiece());
    List<Item> z = new ArrayList<Item>();
    z.add(a);
    z.add(b);
    z.add(c);
    z.add(d);

    return z;
  }

  @Override
  public KeyItem getKeyItem() {
    return new KeyItem("key", new Vector(220, 220));
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

}
