package edu.brown.cs.dreamteam.networking;

import org.eclipse.jetty.websocket.api.Session;

public class PlayerSession {

  private String id;
  private Session session;
  private String username;

  public PlayerSession(String id, Session session) {
    this.id = id;
    this.session = session;
    this.username = "Guest";
  }

  public String getUserName() {
    return username;
  }

  public void setUserName(String name) {
    this.username = name;
  }

  /**
   * Returns a unique ID
   * 
   * @return
   */
  public String getId() {
    return id;
  }

  public Session getSession() {
    return session;
  }

}
