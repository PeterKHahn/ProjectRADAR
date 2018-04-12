package edu.brown.cs.dreamteam.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import edu.brown.cs.dreamteam.entity.GamePlayer;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.utility.Logger;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main Architect for the project, where all functionality integration the
 * many components exist
 * 
 * @author peter
 *
 */
public class SystemArchitect extends Architect {

  private GameEngine game;
  private Rooms rooms = new Rooms();
  private Map<String, ClientState> clientStates;

  public SystemArchitect() {
    init();
  }

  private void init() {
    game = new GameEngine(this);
    game.addGameEventListener(this);
    clientStates = Maps.newConcurrentMap();
  }

  public void initSpark() {
    System.out.println("a");
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    Spark.webSocket("/xx/websocket", GameWebSocketHandler.class);
    // Setup Spark Routes
    Spark.get("/", new HomeHandler(), freeMarker);
    Spark.get("/create", new CreateHandler(), freeMarker);
    Spark.get("/join", new JoinHandler(), freeMarker);
    Spark.get("/game/:roomID", new GameHandler(), freeMarker);
    Spark.post("/giveStatus", new SendStatusHandler(this));
    // EXPERIMENTAL

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
  public void onGameChange(ChunkMap chunks) {
    Collection<GamePlayer> movingThings = chunks.getPlayers();
    Double radius = 5.0;
    for (GamePlayer p : movingThings) {
      Collection<Chunk> chunksNeeded = chunks.getChunksNearPlayer(p, radius);
      chunks.dynamicFromChunks(chunksNeeded);
      chunks.staticFromChunks(chunksNeeded);
    }
  }

  @Override
  public void putClientState(String name, ClientState state) {
    clientStates.put(name, state);
  }

  /**
   * the handler for on start of the game page.
   *
   * @author anina
   */
  private class GameHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      String room = arg0.params(":roomID");
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Game R.A.D.A.R.").put("roomID", room).build();
      return new ModelAndView(variables, "game.ftl");
    }
  }

  /**
   * the handler for on start of the homepage.
   *
   * @author anina
   */
  private class JoinHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      QueryParamsMap qm = arg0.queryMap();
      List<String> room = new ArrayList<>(rooms.getNotPlayingYetRoomIDs());
      String codename = qm.value("codename");
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Join R.A.D.A.R.").put("codename", codename)
          .put("roomIDs", room).build();
      return new ModelAndView(variables, "join.ftl");
    }
  }

  /**
   * the handler for on start of the homepage.
   *
   * @author anina
   */
  private class CreateHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      String newRoomID = rooms.generateNewRoom();
      QueryParamsMap qm = arg0.queryMap();
      String codename = qm.value("codename");
      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Create R.A.D.A.R.").put("codename", codename)
          .put("newRoomID", newRoomID).build();
      return new ModelAndView(variables, "create.ftl");
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

      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "R.A.D.A.R.").build();
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
