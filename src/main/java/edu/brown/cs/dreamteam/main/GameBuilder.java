package edu.brown.cs.dreamteam.main;

import java.util.Collection;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.game.GameMap;
import edu.brown.cs.dreamteam.utility.Logger;

public class GameBuilder {

  private int numHumanPlayers = 0;
  private Collection<GamePlayer> players;

  private GameEngine engine;

  private GameBuilder(Architect architect) {
    this.engine = new GameEngine(architect);
  }

  public GameBuilder addHumanPlayers(Collection<PlayerSession> playerSessions) {
    for (PlayerSession ps : playerSessions) {
      addHumanPlayer(ps);
    }
    return this;
  }

  public GameBuilder addHumanPlayer(PlayerSession playerSession) {
    GamePlayer player = new GamePlayer(playerSession.getId(), 0, 0);

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
    return this;
  }

  public static GameBuilder create(Architect architect) {
    return new GameBuilder(architect);
  }

  public GameEngine complete() {
    while (numHumanPlayers < 4) {
      engine.addAiPlayer();
      numHumanPlayers++;
    }

    return engine;
  }

}
