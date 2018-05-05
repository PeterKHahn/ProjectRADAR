package edu.brown.cs.dreamteam.main;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.ai.AiController;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.DummyPlayer;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Marker;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.item.KeyItem;
import edu.brown.cs.dreamteam.utility.Logger;

public class GameBuilder {

  private int numPlayers;
  private int numDummyPlayers;

  private int height;
  private int width;
  private int chunkSize;

  private int numHumanPlayers = 0;
  private Collection<GamePlayer> players;

  private Vector[] startPositions;
  private int startIndex = 0;

  private GameEngine engine;

  private GameBuilder(GameMap gm, Room r) {
    this.height = gm.getHeight();
    this.width = gm.getWidth();
    this.chunkSize = gm.getChunkSize();
    this.numPlayers = gm.numPlayers();
    this.numDummyPlayers = gm.numDummyPlayers();
    this.engine = new GameEngine(height, width, chunkSize, r);
    startPositions = new Vector[4];
    startPositions[0] = engine.CENTER.add(new Vector(10, 0));
    startPositions[1] = engine.CENTER.add(new Vector(-10, 0));
    startPositions[2] = engine.CENTER.add(new Vector(0, -10));
    startPositions[3] = engine.CENTER.add(new Vector(0, 10));

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

    if (players.size() > 3) {
      Logger.logError("Only 4 players can be in a game. Not all players added");
      return this;
    }
    Vector center = startPositions[startIndex];
    startIndex++;

    GamePlayer p = GamePlayer.player(sessionId, center.x, center.y);

    engine.addPlayer(p);

    numHumanPlayers++;
    return this;

  }

  public static GameBuilder create(GameMap gm, Room r) {
    return new GameBuilder(gm, r);
  }

  public GameEngine complete() {

    for (int i = numHumanPlayers; i < numPlayers; i++) {
      AiController controller = new AiController(Integer.toString(i),
          engine.getBoard(), startPositions[startIndex].x,
          startPositions[startIndex].y);
      engine.addAiPlayer(controller);
      startIndex++;
    }
    for (int i = 0; i < numDummyPlayers; i++) {
      engine.addPlayer(
          new DummyPlayer("dum" + i, engine.CENTER.x, engine.CENTER.y));
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
