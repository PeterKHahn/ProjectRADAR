package edu.brown.cs.dreamteam.main;

import java.util.HashMap;

import org.eclipse.jetty.websocket.api.Session;

import edu.brown.cs.dreamteam.game.GameEngine;

public class Room {
  String roomID;
  private HashMap<Session, String> players;
  private int associatedThread;

  private GameEngine game;

  public Room(String roomID) {
    this.roomID = roomID;
  }

  public void startRoom() {
  }

  public void addPlayer(Session playerSession, String playerID) {
    players.put(playerSession, playerID);
  }

}
