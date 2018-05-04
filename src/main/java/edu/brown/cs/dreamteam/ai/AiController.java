package edu.brown.cs.dreamteam.ai;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Playable;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * A class that controls 1 AI player in the game managed by the given architect.
 *
 * @author efu2
 */
public class AiController {

  private enum StrategyType {
    GATHER, OFFENSE, DEFENSE, GOAL
  }

  private StrategyType strategy;
  private Map<StrategyType, Strategy> strategies;
  private AiPlayer player;

  /**
   * Initializes the AiController that makes decisions about the AiPlayer's next
   * move.
   * 
   * @param id
   *          This AI player's ID.
   * @param board
   *          The graph representation of this game.
   */
  public AiController(String id, Board board, double x, double y) {
    // TODO update starting position
    player = new AiPlayer(id, x, y, Playable.SIZE);
    player.setController(this);

    // Initialize strategies
    strategy = StrategyType.GATHER;
    strategies = new HashMap<>();
    strategies.put(StrategyType.GATHER, new GatherStrategy(board, player));
    strategies.put(StrategyType.OFFENSE, new OffensiveStrategy(board, player));
    strategies.put(StrategyType.DEFENSE, new DefensiveStrategy(board, player));
    strategies.put(StrategyType.GOAL, new GoalStrategy(board, player));
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
    if (player.isAlive()) {
      Collection<Chunk> visibleChunks = chunks.chunksInRange(player,
          Playable.VISIBLE_RANGE);
      updateStrategy(chunks, visibleChunks);
      player.setItemPickedFlag(false);
      player.setPrimaryActionFlag(false);
      strategies.get(strategy).makeNextMove(visibleChunks);
    }
  }

  /**
   * Updates the strategy as necessary, according to the current entity info.
   * 
   * @param chunks
   *          A Collection of all visible chunks to the player right now.
   */
  private void updateStrategy(ChunkMap chunkMap, Collection<Chunk> chunks) {
    // Get visible enemies
    Set<DynamicEntity> enemies = ChunkMap.getEnemies(player, chunks);

    if (enemies.size() > 0 && player.hasWeapon()) {
      // There is an enemy in the visible range and the player has a weapon
      // already
      // if (player.getHealth() > OffensiveStrategy.BASELINE_HEALTH) {
      // // Player's health is above the baseline for it to attack
      // strategy = StrategyType.OFFENSE;
      // } else {
      strategy = StrategyType.DEFENSE;
      // }
    } else {
      // No enemy in visible range or the player doesn't have a weapon yet
      // TODO Check if the goal is revealed
      strategy = StrategyType.GATHER;
    }

    Iterator<Entry<StrategyType, Strategy>> it = strategies.entrySet()
        .iterator();
    while (it.hasNext()) {
      Entry<StrategyType, Strategy> entry = it.next();
      if (entry.getKey() != strategy) {
        entry.getValue().reset();
      }
    }
  }

}
