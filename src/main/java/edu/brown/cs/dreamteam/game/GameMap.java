package edu.brown.cs.dreamteam.game;

import java.util.Collection;

import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.item.Item;

public interface GameMap {

  public Collection<Obstacle> getObstacles();

  public Collection<Item> getItems();

}
