package edu.brown.cs.dreamteam.datastructures;

public class TwoDVector extends Vector {

  private double x;
  private double y;

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

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

}
