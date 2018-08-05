package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;

import java.util.Collection;

public interface GameMap {

    Collection<Obstacle> getObstacles();

    Collection<Item> getItems();

    KeyItem getKeyItem();

    int getHeight();

    int getWidth();

    int getChunkSize();

    int numPlayers();

    int numDummyPlayers();

}
