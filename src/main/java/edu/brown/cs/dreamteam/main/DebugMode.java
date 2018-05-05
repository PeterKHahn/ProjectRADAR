package edu.brown.cs.dreamteam.main;

/**
 * The Debug mode that can be used for testing different architects
 * with*different implementations**
 * 
 * @author peter
 *
 */
public enum DebugMode {
  DEFAULT(new SystemArchitect());

  private Architect architect;

  DebugMode(Architect architect) {
    this.architect = architect;
  }

  public Architect architect() {
    return architect;
  }

}
