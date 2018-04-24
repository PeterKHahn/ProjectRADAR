package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class Box implements Reach {

  private double radius;

  /**
   * Constructor for a box that takes a point and a radius.
   * 
   * @param radius
   *          the radius of the circle
   */
  public Box(double radius) {
    this.radius = radius;
  }

  public double radius() {
    return radius;
  }

  public boolean collides(Vector center1, Vector center2, Box box) {
    return center1.subtract(center2).magnitude() <= this.radius + box.radius;
  }

  @Override
  public double reach() {
    return radius;
  }

}
