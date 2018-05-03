package edu.brown.cs.dreamteam.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;

import edu.brown.cs.dreamteam.game.GameEngine;
import networking.PlayerSession;

public class Room {
  private String roomID;
  private List<PlayerSession> players;
  private int threadID;
  private boolean started;
  private GameEngine engine;

  private GameEngine game;

  public Room(String roomID) {
    this.roomID = roomID;
    this.started = false;
    players = new ArrayList<>();
  }

  public String getRoomID() {
    return roomID;
  }

  public void start() {
    started = true;
  }

  public boolean isStarted() {
    return started;
  }

  public void setEngineThread(GameEngine engine, int threadID) {
    this.engine = engine;
    this.threadID = threadID;
  }

  public int getThreadID() {
    return threadID;
  }

  public void addPlayer(PlayerSession playerSession) {
    players.add(playerSession);
  }

  public void removePlayer(Session user) {
    for (PlayerSession player : players) {
      if (player.getSession().equals(user)) {
        players.remove(player);
      }
    }
  }

  public int numPlayers() {
    return players.size();
  }

  public List<PlayerSession> getPlayers() {
    return players;
  }

}
