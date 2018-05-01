package edu.brown.cs.dreamteam.main;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.utility.Logger;

public class GameBuilder {
  private static final int NUM_PLAYERS = 4;

  private int numHumanPlayers = 0;
  private Collection<GamePlayer> players;

  private GameEngine engine;

  private GameBuilder(Architect architect) {
    this.engine = new GameEngine(architect);
    init();
  }

  private void init() {
    players = new LinkedList<GamePlayer>();
  }

  public GameBuilder addHumanPlayers(Collection<GamePlayer> playersCollection) {
    for (GamePlayer ps : playersCollection) {
      addHumanPlayer(ps);
    }
    return this;
  }

  public GameBuilder addHumanPlayer(GamePlayer player) {

    if (players.size() > 3) {
      Logger.logError("Only 4 players can be in a game. Not all players added");
      return this;
    }
    engine.addPlayer(player);

    numHumanPlayers++;
    return this;

  }

  public GameBuilder generateMap(GameMap map) {
    Collection<Obstacle> obs = map.getObstacles();
    for (Obstacle ob : obs) {
      engine.addObstacle(ob);
    }
    Collection<Item> items = map.getItems();
    for (Item i : items) {
      engine.addItem(i);
    }
    return this;
  }

  public static GameBuilder create(Architect architect) {
    return new GameBuilder(architect);
  }

  public GameEngine complete() {
    engine.makeBoard();
    while (numHumanPlayers < NUM_PLAYERS) {
      engine.addAiPlayer(numHumanPlayers);
      numHumanPlayers++;
    }

    return engine;
  }

}
