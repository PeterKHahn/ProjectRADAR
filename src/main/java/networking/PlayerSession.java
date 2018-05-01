package networking;

import org.eclipse.jetty.websocket.api.Session;

public class PlayerSession {

  private String id;
  private Session session;

  public PlayerSession(String id, Session session) {
    this.id = id;
    this.session = session;
  }

  /**
   * Returns a unique ID
   * 
   * @return
   */
  public String getId() {
    return id;
  }

}
