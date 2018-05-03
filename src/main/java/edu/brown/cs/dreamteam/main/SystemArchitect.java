package edu.brown.cs.dreamteam.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.brown.cs.dreamteam.debug.DummyGameMap;
import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.utility.Logger;
import freemarker.template.Configuration;
import networking.Messenger;
import networking.PlayerSession;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/***
 * The Main Architect for the project,where all functionality integration
 * the*many components exist**
 * 
 * @author peter
 *
 */
public class SystemArchitect extends Architect {
  private static final Gson GSON = new Gson();

  private GameEngine game;
  private Rooms rooms = new Rooms();
  private Map<String, ClientState> clientStates;
  private AtomicInteger userID = new AtomicInteger(6);

  public SystemArchitect() {
    init();
  }

  private void init() {
    clientStates = Maps.newConcurrentMap();
  }

  public void initSpark() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    Spark.webSocket("/websocket", new GameWebSocketHandler(this));
    // Setup Spark Routes
    Spark.get("/", new HomeHandler(), freeMarker);
    Spark.get("/game/:roomID", new GameHandler(), freeMarker);
    Spark.post("/giveStatus", new SendStatusHandler(this));
    Spark.exception(Exception.class, (e, r, er) -> {
      e.printStackTrace();
    });
  }

  @Override
  public void run() {
    Logger.logMessage("Architect is now running");
    Map<String, Thread> threads = threads();

    for (Thread t : threads.values()) {
      t.start();
    }

  }

  /**
   * Returns a thread safe set of ClientStates.
   *
   * @return
   */
  public Map<String, ClientState> retrieveClientStates() {

    return clientStates;
  }

  private Map<String, Thread> threads() {
    Map<String, Thread> threads = new HashMap<>();
    threads.put("game", new Thread(game, "game"));

    return threads;
  }

  @Override
  public void onGameChange(ChunkMap chunks, int id) {
    Collection<GamePlayer> movingThings = chunks.getPlayers();
    Double radius = 5.0;
    for (GamePlayer p : movingThings) {
      Collection<Chunk> chunksNeeded = chunks.chunksInRange(p, radius);
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("type", "individual").put("player", p)
          .put("entities", chunks.interactableFromChunks(chunksNeeded))
          .put("markers", chunks.markers())
          .put("items", ChunkMap.itemsFromChunks(chunksNeeded)).build();
      broadcastIndividualMessage(p.getId(), GSON.toJson(variables));
    }

  }

  public void broadcastIndividualMessage(String sender, String message) {
    Session relevant = null;
    for (PlayerSession player : rooms.getPlayers()) {
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

  @Override
  public void putClientState(String name, ClientState state) {
    clientStates.put(name, state);
  }

  @WebSocket
  public class GameWebSocketHandler {
    private String sender;
    private String msg;
    private Architect a;

    public GameWebSocketHandler(Architect a) {
      this.a = a;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
      Map<String, List<String>> params = user.getUpgradeRequest()
          .getParameterMap();
      if (params != null && params.containsKey("roomID")) {
        // As the parameter's value is a List, we use 'get(0)'
        String roomID = params.get("roomID").get(0);
        System.out.println("ROOM ID: " + roomID);
        if (roomID != null) {
          Room r = rooms.getNotPlayingRoom(roomID);
          if (r != null) {
            PlayerSession p = new PlayerSession(
                new AtomicInteger(userID.getAndIncrement()).toString(), user);
            r.addPlayer(p);
            rooms.addPlayers(p);
            Messenger.broadcastMessage("Someone has joined!", r);
          }
        }
      }

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
      Map<String, List<String>> params = user.getUpgradeRequest()
          .getParameterMap();
      if (params != null && params.containsKey("roomID")) {
        // As the parameter's value is a List, we use 'get(0)'
        String roomID = params.get("roomID").get(0);
        Room r = rooms.getPlayingRoom(roomID);
        r.removePlayer(user);
        rooms.removePlayer(user);
        if (r != null) {
          if (r.getPlayers().isEmpty()) {
            rooms.stopPlaying(roomID);
          } else {
            Messenger.broadcastMessage("Someone has left!", r);
          }
        }
      }

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
      JsonObject received = GSON.fromJson(message, JsonObject.class);
      ClientState c = null;
      Map<String, List<String>> params = user.getUpgradeRequest()
          .getParameterMap();
      if (params != null && params.containsKey("roomID")) {
        // As the parameter's value is a List, we use 'get(0)'
        String roomID = params.get("roomID").get(0);
        Room r = rooms.getPlayingRoom(roomID);
        switch (received.get("type").getAsString()) {
          case "name":
            break;
          case "game":
            r = rooms.getNotPlayingRoom(roomID);
            rooms.startRoom(roomID, r);
            Logger.logMessage("Creating a new Game");
            GameBuilder builder = GameBuilder.create(a)
                .generateMap(new DummyGameMap());
            List<PlayerSession> hewwo = r.getPlayers();
            for (PlayerSession player : hewwo) {
              builder.addHumanPlayer(player.getId());
            }
            GameEngine engine = builder.complete();

            Thread x = new Thread(engine);
            x.start();
            for (PlayerSession person : r.getPlayers()) {
              System.out.println("ITERATION: " + person.getId());
              putClientState(person.getId(), new ClientState(person.getId()));

            }
            Messenger.broadcastMessage("start", r);
            break;
          case "key":
            c = getClient(r, user);
            if (c != null) {
              switch (received.get("status").getAsString()) {
                case "left":
                  c.leftHeld(received.get("held").getAsBoolean());
                  break;
                case "right":
                  c.rightHeld(received.get("held").getAsBoolean());
                  break;
                case "up":
                  c.forwardHeld(received.get("held").getAsBoolean());
                  break;
                case "down":
                  c.backwardHeld(received.get("held").getAsBoolean());
                  break;
                case "space":
                  c.primaryAction(true);
                  Logger.logDebug("Primary ACtion set to true");
                  break;
                case "f":
                  c.itemPicked(true);
                  break;
                case "r":
                  c.itemDropped(0);
                  break;
                default:
                  System.out
                      .println("EWWOW: key sent that isn't an option...wtf");
                  break;
              }
              putClient(user, c);
            }

            break;
          default:
            System.out.println("hewwo");
            break;
        }
      }
    }

    private void putClient(Session user, ClientState c) {
      for (PlayerSession player : rooms.getPlayers()) {
        if (user.getRemote().equals(player.getSession().getRemote())
            && c != null) {
          clientStates.put(player.getId(), c);
          System.out.println(player.getId());
        }
      }
    }

    private ClientState getClient(Room r, Session user) {
      for (PlayerSession player : r.getPlayers()) {
        if (user.equals(player.getSession())) {
          return clientStates.get(player.getId());
        }
      }
      return null;
    }
  }

  /**
   * the handler for on start of the game page.
   *
   * @author anina
   */
  private class GameHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      String roomID = arg0.params(":roomID");

      // if there's already a game going on, don't let this guy in.
      if (rooms.alreadyPlaying(roomID)) {

        Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
            .put("title", "Game R.A.D.A.R.").build();
        return new ModelAndView(variables, "error.ftl");
      }
      // if there's already four players, don't let this guy in.
      if (rooms.isNotPlaying(roomID)) {
        Room r = rooms.getNotPlayingRoom(roomID);
        if (r.numPlayers() == 4) {
          Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
              .put("title", "Game R.A.D.A.R.").build();
          return new ModelAndView(variables, "error.ftl");
        }
      } else {
        Room r = new Room(roomID);
        rooms.addNotPlayingRoom(roomID, r);
      }

      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Game R.A.D.A.R.").put("roomID", roomID).build();
      return new ModelAndView(variables, "game.ftl");
    }
  }

  /**
   * the handler for on start of the homepage.
   *
   * @author anina
   */
  private class HomeHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      String newRoomId = rooms.generateNewRoom();
      Room r = new Room(newRoomId);
      rooms.addNotPlayingRoom(newRoomId, r);
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "R.A.D.A.R.").put("roomID", newRoomId).build();
      return new ModelAndView(variables, "home.ftl");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   * @author jj
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

}
