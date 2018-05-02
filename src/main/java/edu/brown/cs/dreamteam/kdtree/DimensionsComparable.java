package edu.brown.cs.dreamteam.kdtree;

/**
 * Interface that reinforces a class to be comparable by its specific
 * dimensions.
 *
 * @param <T> The type of element that the class implementing this interface is
 * comparable to
 * @author efu2
 */
public interface DimensionsComparable<T> {
  /**
   * Compares against the given element at a specific dimension.
   * @param other The element to compare against. Assumes that the element has
   * at least {@code dimension} dimensions
   * @param dimension The dimension to use for comparison
   * @return 1 if this is greater than {@code other}, 0 if they are equal, -1 if
   * this is less than {@code other}
   */
  int compareTo(T other, int dimension);
}
