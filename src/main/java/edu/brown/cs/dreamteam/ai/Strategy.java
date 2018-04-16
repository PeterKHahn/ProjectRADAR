package edu.brown.cs.dreamteam.ai;

/**
 * An interface defining a game mode's strategy to control the AI player.
 *
 * @author efu2
 */
public interface Strategy {
  // TODO: check return type (should be able to give next "key presses" for
  // moving and interacting with items) AND parameter type for player state
  void getNextMove(String entities);
}
