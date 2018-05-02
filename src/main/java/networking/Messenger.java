package networking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.dreamteam.main.Room;

public class Messenger {
  private static final Gson GSON = new Gson();

  // Sends a message from one user to all users, along with a list of current
  // usernames
  public static void broadcastMessage(String message, Room r) {
    Map<String, Object> variables;
    List<String> playerNames = new ArrayList<>();
    for (PlayerSession player : r.getPlayers()) {
      playerNames.add(player.getUserName());
    }

    r.getPlayers().stream().forEach(session -> {
      try {
        session.getSession().getRemote()
            .sendString(String
                .valueOf(GSON.toJson(new ImmutableMap.Builder<String, Object>()
                    .put("message", message).put("type", "gameMessage")
                    .put("userlist", playerNames).build())));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

  }

  public static void broadcastIndividualMessage(String sender, String message,
      Room r) {
    Session relevant = null;
    for (PlayerSession player : r.getPlayers()) {
      if (player.getId().equals(sender)) {
        relevant = player.getSession();
      }
    }
    if (relevant != null) {
      try {
        relevant.getRemote().sendString(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
