package edu.brown.cs.dreamteam.ai;

import java.util.Collection;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.Chunk;

/**
 * Strategy to gather as many items as possible. No other players are in the
 * visible range. Cases: - AI currently has insufficient materials to make a
 * radar - Get all the best-quality items in the visible range - If no items are
 * in the visible range, choose a random direction and continue in that
 * direction until encountering items or other players (and change modes
 * accordingly) - AI has sufficient materials to make a radar - If any
 * better-quality items are in the visible range, pick them up - Otherwise, make
 * a radar and decide its placement - If no radars have been placed yet, place
 * it right where the AI is - If one radar has been placed, place it somewhere
 * such that the radar radii don't overlap - If multiple radars have been
 * placed, go towards the midpoint of existing radars' centers and place the
 * radar there
 * 
 * @author efu2
 */
public class GatherStrategy extends Strategy {

  public GatherStrategy(Board board, AiPlayer player) {
    super(board, player);
    // TODO Auto-generated constructor stub
  }

  private void placeRadar(Collection<Chunk> chunks) {
    // TODO: calculate the position to place the radar at.
  }

  /**
   * Checks whether the AI player has enough materials to make a radar.
   * 
   * @param entities
   *          The entity information given by networking.
   * @return True if there are sufficient materials, false otherwise.
   */
  private boolean canMakeRadar(Collection<Chunk> chunks) {
    // TODO
    return false;
  }

  @Override
  void makeNextMove(Collection<Chunk> chunks) {
    // TODO
    if (canMakeRadar(chunks)) {
      placeRadar(chunks);
    } else {

    }
    // TODO call player.updateDynamic(vertCoeff, horzCoeff)
  }
}
