package edu.brown.cs.dreamteam.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;

// note: radius of obstacles hs to be at least 15 to be valid,
// unless we want the pick-up items to be literal specks
public class DummyGameMap implements GameMap {

  @Override
  public Collection<Obstacle> getObstacles() {
    Obstacle x = new Obstacle("x", new Vector(30, 30), 25);
    Obstacle y = new Obstacle("y", new Vector(50, 50), 3);
    List<Obstacle> z = new ArrayList<>();
    z.add(x);
    z.add(y);
    return z;
  }

}
