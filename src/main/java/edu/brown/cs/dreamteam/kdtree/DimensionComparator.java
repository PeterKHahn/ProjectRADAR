package edu.brown.cs.dreamteam.kdtree;

import java.util.Comparator;

/**
 * Comparator for any class that implements the DimensionsComparable interface.
 * E.g. Star in the CS32 Stars project.
 *
 * @param <T> The type of object that this comparator compares.
 * @author efu2
 */
public class DimensionComparator<T extends DimensionsComparable<T>>
    implements Comparator<T> {
  private int compareDimension = 1;

  @Override
  public int compare(T o1, T o2) {
    return o1.compareTo(o2, this.compareDimension);
  }

  /**
   * Set which dimension of the T objects' coordinates to compare.
   *
   * @param dimension The dimension to compare. Assumed to be less than type T's
   * maximum dimension.
   */
  public void setCompareDimension(int dimension) {
    this.compareDimension = dimension;
  }
}
