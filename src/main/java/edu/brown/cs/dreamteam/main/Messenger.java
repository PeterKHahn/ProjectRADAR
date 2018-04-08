package edu.brown.cs.dreamteam.main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class Messenger {
  private static final Gson GSON = new Gson();
  static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
  static int nextUserNumber = 1;

  public static void addUserUserID(Session user) {
    String userID = "User" + Messenger.nextUserNumber++;
    userUsernameMap.put(user, userID);
    nextUserNumber++;
  }

  // Sends a message from one user to all users, along with a list of current
  // usernames
  public static void broadcastMessage(String sender, String message) {
    Map<String, Object> variables;
    userUsernameMap.keySet().stream().filter(Session::isOpen)
        .forEach(session -> {
          try {
            session.getRemote()
                .sendString(String.valueOf(
                    GSON.toJson(new ImmutableMap.Builder<String, Object>()
                        .put("user", sender).put("message", message)
                        .put("userlist", userUsernameMap.values()).build())));

          } catch (Exception e) {
            e.printStackTrace();
          }
        });

  }
}
