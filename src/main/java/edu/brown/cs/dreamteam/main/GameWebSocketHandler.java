package edu.brown.cs.dreamteam.main;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class GameWebSocketHandler {
  private String sender, msg;

  @OnWebSocketConnect
  public void onConnect(Session user) throws Exception {
    System.out.println("HOI!");
    Messenger.addUserUserID(user);
    Messenger.broadcastMessage(sender = "Server",
        msg = ("Someone joined the chat!"));
  }

  @OnWebSocketClose
  public void onClose(Session user, int statusCode, String reason) {
    System.out.println("BOI!");
    String username = Messenger.userUsernameMap.get(user);
    Messenger.userUsernameMap.remove(user);
    Messenger.broadcastMessage(sender = "Server",
        msg = (username + " left the chat"));
  }

  @OnWebSocketMessage
  public void onMessage(Session user, String message) {
    System.out.println("NOICE");
    Messenger.broadcastMessage(sender = Messenger.userUsernameMap.get(user),
        msg = message);
  }
}
