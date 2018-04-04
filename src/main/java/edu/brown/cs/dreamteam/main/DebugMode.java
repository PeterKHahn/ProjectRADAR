package edu.brown.cs.dreamteam.main;

import edu.brown.cs.dreamteam.debug.DummySyncArchitect;

/**
 * The Debug mode that can be used for testing different architects with
 * different implementations
 * 
 * @author peter
 *
 */
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
