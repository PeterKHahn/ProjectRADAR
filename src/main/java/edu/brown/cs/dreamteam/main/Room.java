package edu.brown.cs.dreamteam.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.entity.Interactable;
import edu.brown.cs.dreamteam.entity.Marker;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.event.GameEventListener;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.item.Item;
import networking.PlayerSession;

public class Room implements GameEventListener {
  private String roomID;
  private List<PlayerSession> players;
  private int threadID;
  private boolean started;
  private GameEngine engine;
  private Map<String, ClientState> clientStates;
  private static final Gson GSON = new Gson();

  private GameEngine game;

  public Room(String roomID) {
    this.roomID = roomID;
    this.started = false;
    players = new ArrayList<>();
    clientStates = Maps.newConcurrentMap();
  }

  /**
   * Returns a Map of client ids to clientstates that exist within the
   * Architecture
   * 
   * @return a map of client ids to clientstates that exist within the
   *         Architecture
   */
  public Map<String, ClientState> retrieveClientStates() {

    return clientStates;
  }

  public void putClientState(String name, ClientState state) {
    clientStates.put(name, state);
  }

  public void putNewClient(String id, Session user) {
    clientStates.put(id, new ClientState(id));
  }

  public void putOldClient(Session user, ClientState c) {
    if (c != null) {
      for (PlayerSession player : players) {
        if (player.getSession().equals(user)) {
          clientStates.put(player.getId(), c);
        }
      }
    }
  }

  public ClientState getClient(Session user) {
    for (PlayerSession player : players) {
      if (user.equals(player.getSession())) {
        return clientStates.get(player.getId());
      }
    }
    return null;
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

  @Override
  public void onGameChange(ChunkMap chunks) {
    Collection<GamePlayer> movingThings = chunks.getPlayers();
    double radius = 300.0;
    for (GamePlayer p : movingThings) {
      Collection<Chunk> chunksNeeded = chunks.chunksInRange(p, radius);
      Map<String, Object> variables;
      List<Map<String, Object>> interactables = new ArrayList<>();
      List<Map<String, Object>> items = new ArrayList<>();
      List<Map<String, Object>> markers = new ArrayList<>();

      // parse out interactables
      for (Interactable x : chunks.interactableFromChunks(chunksNeeded)) {

        variables = new ImmutableMap.Builder<String, Object>()
            .put("x", x.center().x).put("y", x.center().y)
            .put("collisionBox", x.collisionBox()).put("hurtBox", x.hurtBox())
            .put("drawType", x.getDrawType()).put("hitBox", x.hitBox())
            .put("id", x.getId()).build();
        interactables.add(variables);
      }
      // parse out items
      for (Item i : ChunkMap.itemsFromChunks(chunksNeeded)) {
        variables = new ImmutableMap.Builder<String, Object>()
            .put("x", i.center().x).put("y", i.center().y)
            .put("type", i.getType()).put("id", i.getId()).build();
        items.add(variables);
      }

      // parse out player
      Map<String, Object> z = new ImmutableMap.Builder<String, Object>()
          .put("x", p.center().x).put("y", p.center().y).put("id", p.getId())
          .put("type", "human").put("health", p.getHealth())
          .put("radius", p.getRadius()).build();

      for (Marker m : chunks.markers()) {
        variables = new ImmutableMap.Builder<String, Object>()
            .put("x", m.center().x).put("y", m.center().y)
            .put("radius", m.getRadius()).build();
        markers.add(variables);
      }
      Map<String, String> idToNames = new HashMap<>();
      for (PlayerSession a : players) {
        idToNames.put(a.getId(), a.getUserName());
      }

      variables = new ImmutableMap.Builder<String, Object>()
          .put("type", "individual").put("player", z)
          .put("playerList", idToNames).put("interactables", interactables)
          .put("markers", markers).put("items", items)
          .put("weapon", p.getInventory().getActiveWeapon()).build();
      broadcastIndividualMessage(p.getId(), GSON.toJson(variables));
    }

  }

  public void broadcastIndividualMessage(String sender, String message) {
    Session relevant = null;
    for (PlayerSession player : players) {
      if (player.getId().equals(sender)) {
        relevant = player.getSession();
      }
    }

    if (relevant != null && relevant.getRemote() != null && relevant.isOpen()) {
      try {
        relevant.getRemote().sendString(message);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

}
