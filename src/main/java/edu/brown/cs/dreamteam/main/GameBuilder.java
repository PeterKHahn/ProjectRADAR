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
import edu.brown.cs.dreamteam.utility.Logger;

public class GameBuilder {

  private static final int NUM_PLAYERS = 4;

  private static final int HEIGHT = 1;
  private static final int WIDTH = 1;
  private static final int CHUNK_SIZE = 200;

  private int numHumanPlayers = 0;
  private Collection<GamePlayer> players;

  private GameEngine engine;

  private GameBuilder(Architect architect) {
    this.engine = new GameEngine(HEIGHT, WIDTH, CHUNK_SIZE, architect);

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

  public GameBuilder generateMap(GameMap map) {
    Collection<Obstacle> obs = map.getObstacles();
    for (Obstacle ob : obs) {
      engine.addStatic(ob);
    }
    Collection<Item> items = map.getItems();
    for (Item i : items) {
      engine.addItem(i);
    }
    engine.board();
    return this;
  }

  public static GameBuilder create(Architect architect) {
    return new GameBuilder(architect);
  }

  public GameEngine complete() {
    while (numHumanPlayers < NUM_PLAYERS) {
      engine.addAiPlayers(numHumanPlayers);
      numHumanPlayers++;
    }
    Marker bottomLeft = new Marker(new Vector(0, 0));
    Marker bottomRight = new Marker(new Vector(WIDTH * CHUNK_SIZE, 0));
    Marker topLeft = new Marker(new Vector(0, WIDTH * CHUNK_SIZE));
    Marker topRight = new Marker(
        new Vector(WIDTH * CHUNK_SIZE, WIDTH * CHUNK_SIZE));

    engine.addMarker(bottomLeft);
    engine.addMarker(topLeft);
    engine.addMarker(topRight);
    engine.addMarker(bottomRight);

    return engine;
  }

}
