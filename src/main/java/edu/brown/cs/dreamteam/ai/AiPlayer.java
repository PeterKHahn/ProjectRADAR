package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.event.ClientState;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that receives the AI player's client status updates from networking
 * and sends networking the position updates.
 *
 * @author efu2
 */
public class AiPlayer {
  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE
  }

  private StrategyType strategy;
  private Map<StrategyType, Strategy> strategies;
  private Position position;

  /**
   * Initializes the AI player. TODO: Check entities type from networking
   */
  public AiPlayer(String id, Map<String, ClientState> clientStates) {
    strategy = StrategyType.GATHER;
    strategies = new HashMap<>();
    strategies.put(StrategyType.GATHER, new GatheringStrategy());
    strategies.put(StrategyType.OFFENSE, new OffensiveStrategy());
    strategies.put(StrategyType.DEFENSE, new DefensiveStrategy());

    // TODO: Initialize the coordinates
    position = new Position(0, 0);

  }

  /**
   * Generates a graph using the given entity information at the beginning of
   * the game.
   */
  public static void initGraph(String entities) {

  }

  /**
   * Gets the next action updates of the AI player, including position changes,
   * game actions (key presses to use weapons/place radars), given the player's
   * current state.
   *
   * @param entities
   *          The collection of all static and dynamic entities and
   *          items/weapons at the current tick of the game.
   */
  public void getUpdate(String entities) {
    // TODO: return type (check with networking)
    // TODO: Check entities type from networking and change strategies
    // accordingly
    updateStrategy(entities);
    strategies.get(strategy).getNextMove(entities);
  }

  /**
   * Updates the strategy as necessary, according to the current entity info.
   * 
   * @param entities
   *          Information about all entities.
   */
  private void updateStrategy(String entities) {
    // TODO
  }

}
