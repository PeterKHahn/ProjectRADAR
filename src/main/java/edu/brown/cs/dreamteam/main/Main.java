package edu.brown.cs.dreamteam.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

  private static final int DEFAULT_PORT = 4567;
  private String[] args;
  private static final Gson GSON = new Gson();
  private Rooms rooms = new Rooms();

  Map<String, ClientState> clientStates();

  // EXPERIMENTAL
  static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
  static int nextUserNumber = 1; // Used for creating the next username
  // EXPERIMENTAL

  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    // EXPERIMENTAL
    // Spark.webSocket("/game/:roomID", GameWebSocketHandler.class);
    // Setup Spark Routes
    Spark.get("/", new HomeHandler(), freeMarker);
    Spark.get("/create", new CreateHandler(), freeMarker);
    Spark.get("/join", new JoinHandler(), freeMarker);
    Spark.get("/game/:roomID", new GameHandler(), freeMarker);
    Spark.post("/giveStatus", new SendStatusHandler());

    Spark.exception(Exception.class, (e, r, er) -> {
      e.printStackTrace();
    });

  }

  /**
   * the handler for on start of the game page.
   *
   * @author anina
   */
  private class SendStatusHandler implements Route {

    @Override
    public Object handle(Request arg0, Response arg1) throws Exception {
      String room = arg0.params(":roomID");
      QueryParamsMap qm = arg0.queryMap();
      String codename = qm.value("codename");
      String status = qm.value("status");

      Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
          .put("title", "Game R.A.D.A.R.").put("roomID", room).build();
      return GSON.toJson(variables);
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
