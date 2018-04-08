package edu.brown.cs.dreamteam.ai;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * A class that predicts the AI player's next position, considering the AI
 * player's playing mode and the machine learning model's enemy player position
 * predictions.
 *
 * @author efu2
 */
public class PositionPredictor {
  // private Model hmm;

  /**
   * Initialize a position predictor for an AI player with the gathering
   * strategy.
   */
  public PositionPredictor() {
    // hmm = new HiddenSemiMarkovModel("");
  }

  /**
   * Predicts the next position of the given player based on their velocity and
   * current position.
   *
   * @param playerState
   *          The representation of the enemy player of interest that contains
   *          its position and velocity information.
   */
  public List<Integer> nextPosition(String playerState) {
    // TODO: Check type of player state
    return ImmutableList.of(0, 0);
  }
}
