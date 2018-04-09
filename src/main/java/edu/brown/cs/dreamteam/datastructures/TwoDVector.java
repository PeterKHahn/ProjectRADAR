package edu.brown.cs.dreamteam.datastructures;

/**
 * An immutable vector of two dimensions. The x coordinate is defined as index 0
 * and y coordinate is defined as index 1 of the vector
 *
 * @author peter
 *
 */
public class TwoDVector extends Vector {

  private double x;
  private double y;

  /**
   * Constructor for the TwoDVector given an x and a y
   * 
   * @param x
   * @param y
   */
  public TwoDVector(double x, double y) {
    super(2);
    this.x = x;
    this.y = y;
  }

  @Override
  public double getIndex(int index) {
    if (!isLegalDimension(index)) {
      throw new IndexOutOfBoundsException();
    }
    return index == 0 ? x : y;
  }

  /**
   * Returns the x Value
   * 
   * @return the x Value
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y value
   * 
   * @return the y value
   */
  public double getY() {
    return y;
  }

}
