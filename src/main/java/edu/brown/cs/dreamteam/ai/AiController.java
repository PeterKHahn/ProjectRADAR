package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.ChunkMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that controls 1 AI player in the game managed by the given architect.
 *
 * @author efu2
 */
public class AiController {
  private static final int AI_SIZE = 5;

  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE
  }

  private StrategyType strategy;
  private Map<StrategyType, Strategy> strategies;
  private AiPlayer player;
  private Board board;

  /**
   * Initializes the AI player. TODO: Check entities type from networking
   */
  public AiController(String id, Board board) {
    player = new AiPlayer(id, 0, 0, AI_SIZE);
    strategy = StrategyType.GATHER;
    strategies = new HashMap<>();
    strategies.put(StrategyType.GATHER, new GatheringStrategy());
    strategies.put(StrategyType.OFFENSE, new OffensiveStrategy());
    strategies.put(StrategyType.DEFENSE, new DefensiveStrategy());

  }

  public AiPlayer getPlayer() {
    return player;
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
    updateStrategy(chunks);
    strategies.get(strategy).makeNextMove(chunks, player);
  }

  /**
   * Updates the strategy as necessary, according to the current entity info.
   * 
   * @param chunks
   *          Information about all entities.
   */
  private void updateStrategy(ChunkMap chunks) {
    // Checks if there is an enemy in the visible range
    if (seeEnemy(chunks)) {

      // No enemy in visible range
    } else {

    }
  }

  /**
   * Checks if there is an enemy in the current visible range.
   * 
   * @param chunks
   *          A ChunkMap with information of all entities in the game.
   * @return True if at least one enemy player (including other AI players) are
   *         in the visible range, false otherwise.
   */
  private boolean seeEnemy(ChunkMap chunks) {

    return false;
  }

}
