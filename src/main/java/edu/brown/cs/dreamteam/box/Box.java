package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.utility.Geometry2D;

public class Box implements Reach {

  private Point center;
  private double radius;

  /**
   * Constructor for a box that takes a point and a radius.
   * 
   * @param center
   *          the point that is the center of the Circle
   * @param radius
   *          the radius of the circle
   */
  public Box(Point center, double radius) {
    this.center = center;
    this.radius = radius;
  }

  public Box(double x, double y, double radius) {
    this.center = new Point(x, y);
    this.radius = radius;
  }

  public Point center() {
    return center;
  }

  public double radius() {
    return radius;
  }

  public boolean collides(Box box) {
    return Geometry2D.distance(this.center(), box.center()) <= this.radius
        + box.radius;
  }

  @Override
  public double reach() {
    return radius;
  }

  public void move(Vector v) {
    center = center.move(v);
  }

}
