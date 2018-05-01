package edu.brown.cs.dreamteam.kdtree;

/**
 * Interface that reinforces a class to be able to return the specified
 * dimension.
 *
 * @param <T> The object type that the class implementing this interface
 * represents its dimensions with.
 * @author efu2
 */
public interface DimensionsQueryable<T> {
  /**
   * Returns the specified dimension.
   *
   * @param dimension The dimension to return. Assumes the class implementing
   * this interface has at least that many dimensions.
   * @return The dimension
   */
  T getDimension(int dimension);
}
