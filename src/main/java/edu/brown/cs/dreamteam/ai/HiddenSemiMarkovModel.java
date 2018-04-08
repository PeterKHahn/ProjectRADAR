package edu.brown.cs.dreamteam.ai;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * A Hidden Semi-Markov Model to predict one enemy player's next position.
 * Assumes coordinates of grids are integers, and this model represents the
 * behavior of one player.
 *
 * @author efu2
 */
public class HiddenSemiMarkovModel extends Model<List<Integer>, LogEntry> {
  private String id;
  private int boardWidth;
  private int boardHeight;
  private List<List<Double>> prior;
  private int numGames;

  // Keys are lists of 2 coordinates, represented by a list of 2 integers
  private Map<Pair<List<Integer>, List<Integer>>, Double> transition;
  private Map<Pair<Integer, List<Integer>>, Double> duration;

  private final int MAX_STEPS_BEFORE_TRANSITION = 5;
  private final int NUM_PLAYERS = 4;

  /**
   * Instantiates a Hidden Semi-Markov Model using the saved training state.
   *
   * @param dataPath
   *          The path to the saved training state.
   */
  public HiddenSemiMarkovModel(String dataPath) {
    super(dataPath);
  }

  /**
   * Instantiates a Hidden Semi-Markov Model for predicting player positions
   * based on data parsed by the given GameLogParser.
   *
   * Assumed to be only called when this model has never been trained before.
   *
   * @param id
   *          The ID of the player that this model represents, used to check
   *          which game log entries this model should train on.
   * @param boardWidth
   *          The number of columns of tiles in the board.
   * @param boardHeight
   *          The number of rows of tiles in the board.
   * @param prior
   *          A normalized 2D array of Doubles representing the probability of a
   *          player to be at each board position initially.
   */
  public void setup(String id, int boardWidth, int boardHeight,
      List<List<Double>> prior) {
    this.id = id;
    this.boardWidth = boardWidth;
    this.boardHeight = boardHeight;
    this.prior = prior;

    transition = new HashMap<>();
    duration = new HashMap<>();
  }

  @Override
  /**
   * Trains this model using the given data. Assumes that the data is a log of a
   * single game.
   */
  void train(List<LogEntry> data) {
    numGames++;

    // Initialize the players' start positions
    List<Integer> prevCoords = ImmutableList.of();
    int offset = 0;
    for (int i = 0; i < NUM_PLAYERS; i++) {
      LogEntry entry = data.get(i);
      String currId = entry.getId();

      // Initialize prev coordinates as the coordinates at the first frame
      if (currId.equals(id)) {
        prevCoords = entry.getCoords();
        offset = i;
        break;
      }
    }
    if (prevCoords.isEmpty()) {
      System.out
          .println("ERROR: No entry in game log corresponding to player " + id);
      return;
    }

    // Initialize the counter for duration at the current position and the
    // helper multisets for counting
    int counter = 1;
    Multiset<Pair<List<Integer>, List<Integer>>> transitionHelper = HashMultiset
        .create();
    Multiset<Pair<Integer, List<Integer>>> durationHelper = HashMultiset
        .create();

    // Assumes the number of entries in the game log is divisible by the number
    // of players since each player should have an entry at every logged frame
    int numData = data.size();

    // Start from the second frame (the first frame was used for initialization)
    for (int i = 1; i < numData / NUM_PLAYERS; i++) {
      LogEntry entry = data.get(i * NUM_PLAYERS + offset);
      List<Integer> currCoords = entry.getCoords();

      if (currCoords.equals(prevCoords)) {
        // Player stayed in the same board grid so add one count
        counter++;
      } else {
        // Increment transition count from previous coordinates to curr ones
        transitionHelper.add(ImmutablePair.of(prevCoords, currCoords));

        // Increment duration function at (c, s_{t-1})
        durationHelper.add(ImmutablePair.of(counter, prevCoords));

        // Player moved to a different board grid so remove all counts
        counter = 1;
      }

      prevCoords = currCoords;
    }

    // Normalize the duration and transition helper counts and add it to the
    // duration and transition functions
    normalizeDurationAndTransition(durationHelper, transitionHelper);
  }

  private void normalizeDurationAndTransition(
      Multiset<Pair<Integer, List<Integer>>> durationHelper,
      Multiset<Pair<List<Integer>, List<Integer>>> transitionHelper) {
    // Normalize duration counts and store in instance variable
    int durationSum = durationHelper.size();
    Iterator<Entry<Pair<Integer, List<Integer>>>> durationIt = durationHelper
        .entrySet().iterator();
    while (durationIt.hasNext()) {
      Pair<Integer, List<Integer>> pair = durationIt.next().getElement();
      int count = durationHelper.count(pair);

      // This model has been trained on other game logs before
      if (!duration.isEmpty()) {
        Double prevValue = duration.get(pair);
        // Normalize the count number in the duration sum of this game log, but
        // divide it by the number of games to ensure each game log has equal
        // weight in the actual duration function
        duration.put(pair, ((double) count / durationSum) / numGames
            + (numGames - 1) / numGames * prevValue);
      } else {
        duration.put(pair, (double) count / durationSum);
      }
    }

    // Normalize transition counts and store in instance variable
    int transitionSum = transitionHelper.size();
    Iterator<Entry<Pair<List<Integer>, List<Integer>>>> transitionIt = transitionHelper
        .entrySet().iterator();
    while (transitionIt.hasNext()) {
      Pair<List<Integer>, List<Integer>> pair = transitionIt.next()
          .getElement();
      int count = transitionHelper.count(pair);

      // This model has been trained on other game logs before
      if (!transition.isEmpty()) {
        Double prevValue = transition.get(pair);
        // Normalize the count number in the transition sum of this game log,
        // but divide it by the number of games to ensure each game log has
        // equal weight in the actual transition function
        transition.put(pair, ((double) count / transitionSum) / numGames
            + (numGames - 1) / numGames * prevValue);
      } else {
        transition.put(pair, (double) count / transitionSum);
      }
    }
  }

  @Override
  List<Integer> predict(LogEntry sample) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void loadTrainedModel(String dataPath) {
    numGames = 0;
    File dataFile = new File(dataPath);
    if (dataFile.exists()) {
      // TODO: Load number of games trained on, board width and height, duration
      // and transition functions
    }
  }

}
