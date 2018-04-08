package edu.brown.cs.dreamteam.ai;

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

  /**
   * Initializes the AI player.
   */
  public AiPlayer() {
    strategy = StrategyType.GATHER;
    strategies = new HashMap<>();
    strategies.put(StrategyType.GATHER, new GatheringStrategy());
    strategies.put(StrategyType.OFFENSE, new OffensiveStrategy());
    strategies.put(StrategyType.DEFENSE, new DefensiveStrategy());
  }

  /**
   * Trains machine learning models if data files exist.
   */
  private void trainModels() {
    // TODO
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
    strategies.get(strategy).getNextMove(entities);
  }

}
