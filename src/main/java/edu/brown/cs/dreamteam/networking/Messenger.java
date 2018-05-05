package edu.brown.cs.dreamteam.networking;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.dreamteam.main.Room;

public class Messenger {
  private static final Gson GSON = new Gson();

  // Sends a message from one user to all users, along with a list of current
  // usernames
  public static void broadcastMessage(String message, Room r) {
    List<String> playerNames = new ArrayList<>();
    for (PlayerSession player : r.getPlayers()) {
      playerNames.add(player.getUserName());
    }

    r.getPlayers().stream().forEach(session -> {
      try {
        if (session.getSession().isOpen()) {
          session.getSession().getRemote()
              .sendString(String.valueOf(
                  GSON.toJson(new ImmutableMap.Builder<String, Object>()
                      .put("message", message).put("type", "gameMessage")
                      .put("userlist", playerNames).build())));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

  }

}
