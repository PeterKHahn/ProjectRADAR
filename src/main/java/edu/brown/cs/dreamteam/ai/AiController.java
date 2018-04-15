package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that controls 1 AI player in the game managed by the given architect.
 *
 * @author efu2
 */
public class AiController {
  private static final int AI_SIZE = 5;
  private static final int VISIBLE_RANGE = AI_SIZE * 5;

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
      // TODO check player health
    } else {
      // No enemy in visible range

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
    // Get chunks in the visible range
    int currRow = chunks.getChunkRow(player.getYPos());
    int currCol = chunks.getChunkCol(player.getXPos());
    int fromRow = currRow - VISIBLE_RANGE;
    int toRow = currRow + VISIBLE_RANGE;
    int fromCol = currCol - VISIBLE_RANGE;
    int toCol = currCol + VISIBLE_RANGE;
    Collection<Chunk> visibleChunks = chunks.chunksInRange(fromRow, toRow,
        fromCol, toCol);

    // Iterate through all chunks to check if another player is within the
    // chunks in visible range
    for (Chunk chunk : visibleChunks) {
      Collection<DynamicEntity> players = chunk.getDynamicEntities();
      if (players.size() > 0) {
        if (players.contains(player) && players.size() > 1) {
          return true;
        } else if (!players.contains(player)) {
          return true;
        }
      }
    }

    // All chunks have been iterated through
    return false;
  }

}
