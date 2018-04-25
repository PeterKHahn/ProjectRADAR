package edu.brown.cs.dreamteam.ai;

import java.util.Collection;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.Chunk;

/**
 * An interface defining a game mode's strategy to control the AI player.
 *
 * @author efu2
 */
public abstract class Strategy {
  protected Board board;
  protected AiPlayer player;

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
}
