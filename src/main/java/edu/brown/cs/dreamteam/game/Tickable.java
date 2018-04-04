package edu.brown.cs.dreamteam.game;

/**
 * The Tickable interface implements the void tick() method that updates at
 * every game tick
 * 
 * @author peter
 *
 */
public interface Tickable {

  /**
   * Updates the internal event of a Tickable by changing a state marginally
   */
  public void tick();
}
