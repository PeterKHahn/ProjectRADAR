package edu.brown.cs.dreamteam.main;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Marker;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;
import edu.brown.cs.dreamteam.utility.Logger;

public class GameBuilder {

  private static final int NUM_PLAYERS = 4;

  private int height;
  private int width;
  private int chunkSize;

  private int numHumanPlayers = 0;
  private Collection<GamePlayer> players;

  private GameEngine engine;

  private GameBuilder(GameMap gm, Room r) {
    this.height = gm.getHeight();
    this.width = gm.getWidth();
    this.chunkSize = gm.getChunkSize();
    this.engine = new GameEngine(height, width, chunkSize, r);
    Collection<Obstacle> obs = gm.getObstacles();
    for (Obstacle ob : obs) {
      engine.addStatic(ob);
    }
    Collection<Item> items = gm.getItems();
    for (Item i : items) {
      engine.addItem(i);
    }
    KeyItem key = gm.getKeyItem();
    engine.addKeyItem(key);
    engine.board();
    init();
  }

  private void init() {
    players = new LinkedList<GamePlayer>();

  }

  public GameBuilder addHumanPlayer(String sessionId) {
    GamePlayer p = GamePlayer.player(sessionId, engine.CENTER.x,
        engine.CENTER.y);
    if (players.size() > 3) {
      Logger.logError("Only 4 players can be in a game. Not all players added");
      return this;
    }
    engine.addPlayer(p);

    numHumanPlayers++;
    return this;

  }

  public static GameBuilder create(GameMap gm, Room r) {
    return new GameBuilder(gm, r);
  }

  public GameEngine complete() {
    while (numHumanPlayers < NUM_PLAYERS) {
      engine.addAiPlayers(numHumanPlayers);
      numHumanPlayers++;
    }
    Marker bottomLeft = new Marker(new Vector(0, 0));
    Marker bottomRight = new Marker(new Vector(width * chunkSize, 0));
    Marker topLeft = new Marker(new Vector(0, height * chunkSize));
    Marker topRight = new Marker(
        new Vector(width * chunkSize, height * chunkSize));

    engine.addMarker(bottomLeft);
    engine.addMarker(topLeft);
    engine.addMarker(topRight);
    engine.addMarker(bottomRight);

    return engine;
  }

}
