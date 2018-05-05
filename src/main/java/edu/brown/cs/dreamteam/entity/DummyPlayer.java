package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.event.ClientState;

public class DummyPlayer extends Playable {

  public DummyPlayer(String id, double x, double y) {
    super(id, x, y, "DUMMY");
  }

  @Override
  public void update(ClientState state) {
    return;

  }

  @Override
  public String getDrawType() {
    return "none";
  }

}
