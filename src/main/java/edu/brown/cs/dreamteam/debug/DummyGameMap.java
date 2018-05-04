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

  private static final int HEIGHT = 10;
  private static final int WIDTH = 10;
  private static final int CHUNK_SIZE = 50;

  @Override
  public Collection<Obstacle> getObstacles() {

    Obstacle x = new Obstacle("x", new Vector(50, 15), 10);
    Obstacle y = new Obstacle("y", new Vector(50, 50), 10);
    Obstacle z = new Obstacle("z", new Vector(50, 85), 10);
    Obstacle off = new Obstacle("off", new Vector(0, 0), 50);

    List<Obstacle> ls = new ArrayList<>();
    for (int i = 1; i < WIDTH / 2; i++) {
      for (int j = 1; j < HEIGHT / 2; j++) {
        Obstacle o = new Obstacle("Ob" + (i * HEIGHT / 2 + j),
            new Vector(i * CHUNK_SIZE * 2, j * CHUNK_SIZE * 2), 10);
        ls.add(o);
      }

    }
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
    List<Item> ls = new ArrayList<Item>();
    for (int i = 1; i < WIDTH / 2; i++) {
      for (int j = 1; j < HEIGHT / 2; j++) {

        if (i * j % 2 == 0) {
          Item it = Item.random("Item" + (i * HEIGHT / 2 + j),
              new Vector(i * CHUNK_SIZE * 2 + CHUNK_SIZE / 2,
                  j * CHUNK_SIZE * 2 + CHUNK_SIZE / 2));
          ls.add(it);
        } else {
          Item it = Item.random("Item" + (i * HEIGHT / 2 + j),
              new Vector(i * CHUNK_SIZE * 2 + CHUNK_SIZE / 2,
                  j * CHUNK_SIZE * 2 + CHUNK_SIZE / 2));
          ls.add(it);
        }

      }

    }
    ls.add(a);
    ls.add(b);
    ls.add(c);
    ls.add(d);

    return ls;
  }

  @Override
  public KeyItem getKeyItem() {
    return new KeyItem("key", new Vector(320, 320));
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
