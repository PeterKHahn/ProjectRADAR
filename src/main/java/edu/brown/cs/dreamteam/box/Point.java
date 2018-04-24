package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class Point {

  public final double x;
  public final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point move(Vector v) {
    return move(v.x, v.y);
  }

  public Point move(double x, double y) {
    return new Point(this.x + x, this.y + y);
  }

}
