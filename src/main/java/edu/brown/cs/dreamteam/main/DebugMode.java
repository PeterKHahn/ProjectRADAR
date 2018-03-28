package edu.brown.cs.dreamteam.main;

import edu.brown.cs.dreamteam.debug.DummySyncArchitect;

public enum DebugMode {
  DEFAULT(new SystemArchitect()), GAME_SYNC(new DummySyncArchitect());

  private Architect architect;

  DebugMode(Architect architect) {
    this.architect = architect;
  }

  public Architect architect() {
    return architect;
  }

}
