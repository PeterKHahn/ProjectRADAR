package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class Marker {

  private final Vector center;
  private final double radius = 5;

  public Marker(Vector center) {
    this.center = center;
  }

  public Marker(double x, double y) {
    center = new Vector(x, y);
  }

  public Vector center() {
    return center;
  }

  public double getRadius() {
    return radius;
  }

}
