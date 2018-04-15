package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.game.Chunk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An interface defining a game mode's strategy to control the AI player.
 *
 * @author efu2
 */
public abstract class Strategy {
  private Board board;
  private AiPlayer player;

  /**
   * Constructs the strategy class.
   * 
   * @param board
   *          A graph representation of the game board.
   * @param player
   *          The AiPlayer this strategy is for.
   */
  public Strategy(Board board, AiPlayer player) {
    this.board = board;
    this.player = player;
  }

  /**
   * Makes the next move based on the specific strategy's goals.
   *
   * @param chunks
   *          Information about all visible entities.
   */
  abstract void makeNextMove(Collection<Chunk> chunks);

  /**
   * Gets all enemies in the current visible range.
   * 
   * @param chunks
   *          A Collection of all visible chunks to the player right now.
   * @return A list of all enemies in the visible range right now.
   */
  public List<DynamicEntity> getVisibleEnemies(Collection<Chunk> chunks) {
    List<DynamicEntity> enemies = new ArrayList<>();

    // Iterate through all chunks
    for (Chunk chunk : chunks) {
      Collection<DynamicEntity> players = chunk.getDynamicEntities();
      enemies.addAll(players);
    }

    // Don't count this player as the enemy
    enemies.remove(player);
    return enemies;
  }
}
