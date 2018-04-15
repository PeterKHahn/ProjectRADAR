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
    GATHER, OFFENSE, DEFENSE, GOAL
  }

  private StrategyType strategy;
  private Map<StrategyType, Strategy> strategies;
  private AiPlayer player;
  private Board board;

  /**
   * Initializes the AiController that makes decisions about the AiPlayer's next
   * move.
   * 
   * @param id
   *          This AI player's ID.
   * @param board
   *          The graph representation of this game.
   */
  public AiController(String id, Board board) {
    // TODO update starting position
    player = new AiPlayer(id, 0, 0, AI_SIZE);

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
    Collection<Chunk> visibleChunks = getVisibleChunks(chunks);
    updateStrategy(visibleChunks);
    strategies.get(strategy).makeNextMove(visibleChunks);
  }

  private Collection<Chunk> getVisibleChunks(ChunkMap chunks) {
    // Get chunks in the visible range
    int currRow = chunks.getChunkRow(player.getYPos());
    int currCol = chunks.getChunkCol(player.getXPos());
    int fromRow = currRow - VISIBLE_RANGE;
    int toRow = currRow + VISIBLE_RANGE;
    int fromCol = currCol - VISIBLE_RANGE;
    int toCol = currCol + VISIBLE_RANGE;
    return chunks.chunksInRange(fromRow, toRow, fromCol, toCol);
  }

  /**
   * Updates the strategy as necessary, according to the current entity info.
   * 
   * @param chunks
   *          A Collection of all visible chunks to the player right now.
   */
  private void updateStrategy(Collection<Chunk> chunks) {
    // Get visible enemies using one of the Strategy instances
    Collection<DynamicEntity> enemies = strategies.get(StrategyType.OFFENSE)
        .getVisibleEnemies(chunks);
    if (enemies.size() > 0) {
      // There is an enemy in the visible range
      // TODO check player health

    } else {
      // No enemy in visible range
      // TODO Check if the goal is revealed
    }
  }

}
