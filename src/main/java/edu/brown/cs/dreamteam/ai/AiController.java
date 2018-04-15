package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Position;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.main.Architect;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that controls 1 AI player in the game managed by the given architect.
 *
 * @author efu2
 */
public class AiController {
  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE
  }

  private StrategyType strategy;
  private Map<StrategyType, Strategy> strategies;
  private Position position;
  private Architect architect;
  private String id;

  /**
   * Initializes the AI player. TODO: Check entities type from networking
   */
  public AiController(String id, Architect architect) {
    this.id = id;
    this.architect = architect;
    strategy = StrategyType.GATHER;
    strategies = new HashMap<>();
    strategies.put(StrategyType.GATHER, new GatheringStrategy());
    strategies.put(StrategyType.OFFENSE, new OffensiveStrategy());
    strategies.put(StrategyType.DEFENSE, new DefensiveStrategy());

    // TODO: Initialize the coordinates
    position = new Position(0, 0);

  }

  /**
   * Gets the next action updates of the AI player, including position changes,
   * game actions (key presses to use weapons/place radars), given the player's
   * current state.
   *
   * @param chunks
   *          The collection of all static and dynamic entities and
   *          items/weapons at the current tick of the game.
   */
  public void makeNextMove(ChunkMap chunks) {
    // TODO: return type (check with networking)
    // TODO: Check entities type from networking and change strategies
    // accordingly
    updateStrategy(chunks);
    ClientState nextState = strategies.get(strategy).makeNextMove(chunks,
        architect.retrieveClientStates().get(id));
    architect.putClientState(id, nextState);
  }

  /**
   * Updates the strategy as necessary, according to the current entity info.
   * 
   * @param chunks
   *          Information about all entities.
   */
  private void updateStrategy(ChunkMap chunks) {
    // TODO
  }

}
