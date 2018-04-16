package edu.brown.cs.dreamteam.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.brown.cs.dreamteam.datastructures.TwoDVector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;

public class DummyGameMap implements GameMap {

  @Override
  public Collection<Obstacle> getObstacles() {
    Obstacle x = new Obstacle("x", new TwoDVector(100, 100),
        new TwoDVector(0, 0));
    Obstacle y = new Obstacle("y", new TwoDVector(200, 200),
        new TwoDVector(300, 300));
    List<Obstacle> z = new ArrayList<>();
    z.add(x);
    z.add(y);
    return z;

  }

}
