package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class Box implements Reach {

  private double radius;
  private Vector offset;

  /**
   * Constructor for a box that takes a point and a radius.
   * 
   * @param radius
   *          the radius of the circle
   */
  public Box(double radius) {
    this.radius = radius;
    this.offset = new Vector(0, 0);
  }

  public Box(double radius, Vector offset) {
    this.offset = offset;
  }

  public double radius() {
    return radius;
  }

  @Override
  public double reach() {
    return radius;
  }

  public Vector offset() {
    return offset;
  }

}
