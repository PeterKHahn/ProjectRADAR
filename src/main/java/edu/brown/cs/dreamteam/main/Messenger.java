package edu.brown.cs.dreamteam.main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class Messenger {
  private static final Gson GSON = new Gson();
  static Map<String, Session> userSessionMap = new ConcurrentHashMap<>();
  static Map<Session, String> sessionUserMap = new ConcurrentHashMap<>();
  static int nextUserNumber = 1;

  public static void addUserUserID(Session user) {
    String userID = "User" + Messenger.nextUserNumber++;
    userSessionMap.put(userID, user);
    sessionUserMap.put(user, userID);
    nextUserNumber++;
  }

  // Sends a message from one user to all users, along with a list of current
  // usernames
  public static void broadcastMessage(String sender, String message) {
    Map<String, Object> variables;
    sessionUserMap.keySet().stream().filter(Session::isOpen)
        .forEach(session -> {
          try {
            session.getRemote()
                .sendString(String.valueOf(
                    GSON.toJson(new ImmutableMap.Builder<String, Object>()
                        .put("user", sender).put("message", message)
                        .put("userlist", sessionUserMap.values()).build())));
          } catch (Exception e) {
            e.printStackTrace();
          }
        });

  }

  public static void broadcastIndividualMessage(String sender, String message) {
    Session relevant = userSessionMap.get(sender);
    if (relevant != null) {
      try {
        relevant.getRemote().sendString(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
