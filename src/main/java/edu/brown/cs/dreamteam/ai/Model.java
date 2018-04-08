package edu.brown.cs.dreamteam.ai;

import java.util.List;

/**
 * An abstract class for a machine learning model.
 * 
 * @param <T>
 *          The type of the output.
 * @param <E>
 *          The type of the data samples.
 * @author efu2
 */
public abstract class Model<T, E> {

  /**
   * Constructs the machine learning model.
   * 
   * @param dataPath
   *          The path to any files that have this model's previously trained
   *          state stored.
   */
  public Model(String dataPath) {
    loadTrainedModel(dataPath);
  }

  /**
   * Loads in a previously trained state if it exists.
   * 
   * @param dataPath
   *          The path to this model's previously trained state.
   */
  protected abstract void loadTrainedModel(String dataPath);

  /**
   * Saves the model's currently trained state to a file so it can be loaded in.
   * 
   * @param dataPath
   *          The path that this model's trained state should be saved at.
   */
  public void saveTrainedModel(String dataPath) {
    // TODO
  }

  /**
   * Trains the model using the given data.
   * 
   * @param data
   *          A list of data samples to train this model.
   */
  abstract void train(List<E> data);

  /**
   * Predicts the unlabelled sample's output using this model.
   * 
   * @param sample
   *          The data sample to predict the label of.
   * @return An output indicating this model's prediction of the given sample.
   */
  abstract T predict(E sample);
}
