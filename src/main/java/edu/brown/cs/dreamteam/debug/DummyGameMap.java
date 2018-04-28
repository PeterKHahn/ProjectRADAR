package edu.brown.cs.dreamteam.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;

public class DummyGameMap implements GameMap {

  @Override
  public Collection<Obstacle> getObstacles() {
    Obstacle x = new Obstacle("x", new Vector(100, 100), 50);
    Obstacle y = new Obstacle("y", new Vector(200, 200), 30);

    List<Obstacle> z = new ArrayList<>();
    z.add(x);
    z.add(y);
    return z;

  }

}
