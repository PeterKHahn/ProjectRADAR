package edu.brown.cs.dreamteam.datastructures;

/**
 * An abstract datastructure that stores dimensioned data, where each element
 * can be retrieved. In addition, there are methods that allow for operations on
 * different vectors
 *
 * @author peter
 *
 */
public abstract class Vector {
  private final int dimensions;

  /**
   * Constructor for a vector with dimensions dimensions.
   *
   * @param dimensions
   *          the number of dimensions this vector will have
   */
  public Vector(int dimensions) {
    this.dimensions = dimensions;
  }

  /**
   * Returns the number of dimensions this vector has.
   *
   * @return the number of dimensions this vector has
   */
  public int getNumDimensions() {
    return dimensions;
  }

  /**
   * Returns the double at the given legal index.
   *
   * @param index
   *          the index we are trying to get the value of vector at
   * @return the value of the vector at index
   */
  public abstract double getIndex(int index);

  /**
   * Returns the unscaled, Euclidean distance between two vectors.
   *
   * @param d
   *          the vector we are comparing to
   * @return the euclidean distance between this and d
   */
  public double distance(Vector d) {

    return Math.sqrt(innerProduct(d));
  }

  /**
   * Returns the unscaled, Euclidean distance squared between two vectors.
   *
   * @param d
   *          the vector which we are hoping to find the distance to, where d is
   *          the same dimension as this.
   * @return a double representing the distance squared between this vector and
   *         d.
   */
  public double distanceSquared(Vector d) {
    return innerProduct(d);
  }

  /**
   * Inner product function that takes the dot product of this vector and d,
   * given that they are of the same dimension.
   *
   * @param d
   *          a vector to find the dot product with, where d is the same
   *          dimension as this.
   * @return
   */
  private double innerProduct(Vector d) {
    if (d.getNumDimensions() != this.getNumDimensions()) {
      throw new IllegalVectorSizeException();
    }
    double sum = 0;
    for (int i = 0; i < getNumDimensions(); i++) {
      double aIndex = this.getIndex(i);
      double bIndex = d.getIndex(i);

      sum += (aIndex - bIndex) * (aIndex - bIndex);
    }

    return sum;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < dimensions; i++) {
      sb.append(getIndex(i) + " ");
    }

    return "<" + sb.toString().trim() + ">";
  }

  /**
   * Checks whether a given dimension falls within the bounds of the vector.
   *
   * @param index
   *          the index that we are checking
   * @return true if the index is within bound, false otherwise
   */
  public boolean isLegalDimension(int index) {
    return index > -1 && index < getNumDimensions();
  }

  /**
   * An exception class that throws upon encountering a vector of illegal size.
   *
   * @author peter
   *
   */
  class IllegalVectorSizeException extends RuntimeException {

    private static final long serialVersionUID = -5918013048369980257L;

    IllegalVectorSizeException() {
      super("Illegal dimension size for vector");
    }
  }
}
