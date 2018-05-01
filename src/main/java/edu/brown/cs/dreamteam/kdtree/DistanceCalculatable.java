package edu.brown.cs.dreamteam.kdtree;

/**
 * Interface that reinforces a class to be able to calculate its distance to
 * another object.
 *
 * @param <T> The type of element that the class implementing this interface
 * can calculate a distance to.
 * @author efu2
 */
public interface DistanceCalculatable<T extends DimensionsComparable<T>> {
  /**
   * Calculates the square of the distance from this object to the given other
   * object.
   *
   * @param other The element to calculate a distance to. Assumes {@code other}
   * has the same number of dimensions as this object.
   * @return A Double representing the square of the Euclidean distance.
   */
  Double distanceTo(T other);
}
