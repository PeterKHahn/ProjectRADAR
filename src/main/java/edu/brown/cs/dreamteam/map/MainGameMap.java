package edu.brown.cs.dreamteam.map;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// note: radius of obstacles hs to be at least 15 to be valid,
// unless we want the pick-up items to be literal specks
public class MainGameMap implements GameMap {

    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static final int CHUNK_SIZE = 50;

    @Override
    public Collection<Obstacle> getObstacles() {

        List<Obstacle> ls = new ArrayList<>();
        for (int i = 1; i < WIDTH / 2; i++) {
            for (int j = 1; j < HEIGHT / 2; j++) {
                Obstacle o = new Obstacle("Ob" + (i * HEIGHT / 2 + j),
                        new Vector(i * CHUNK_SIZE * 2, j * CHUNK_SIZE * 2), 10);
                ls.add(o);
            }

        }

        return ls;
    }

    @Override
    public Collection<Item> getItems() {
        List<Item> ls = new ArrayList<>();
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

        return ls;
    }

    @Override
    public KeyItem getKeyItem() {
        return new KeyItem("key", new Vector(325, 325));
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
        return 4;
    }

    @Override
    public int numDummyPlayers() {
        return 0;
    }

}
