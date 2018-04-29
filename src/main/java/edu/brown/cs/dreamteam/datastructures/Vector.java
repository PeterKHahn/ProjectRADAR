package edu.brown.cs.dreamteam.datastructures;

import edu.brown.cs.dreamteam.box.Point;

/**
 * An immutable vector of two dimensions. The x coordinate is defined as index 0
 * and y coordinate is defined as index 1 of the vector
 *
 * @author peter
 *
 */
public class Vector {

  public final double x;
  public final double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector(Point p) {
    this.x = p.x;
    this.y = p.x;
  }

  public Vector add(Vector v) {
    return new Vector(this.x + v.x, this.y + v.y);
  }

  public Vector subtract(Vector v) {
    return new Vector(this.x + v.x, this.y - v.y);
  }

  public Vector scalarMultiply(double c) {
    return new Vector(this.x * c, this.y * c);
  }

  public double innerProduct(Vector v) {
    return this.x * v.x + this.y * v.y;
  }

  public double magnitudeSquared() {
    return this.innerProduct(this);
  }

  public double magnitude() {
    return Math.sqrt(magnitudeSquared());
  }

  public double projectOntoMagnitude(Vector v) {
    return v.innerProduct(this) / v.magnitudeSquared();
  }

  public Vector projectOnto(Vector v) {
    double factor = this.projectOntoMagnitude(v);
    return v.scalarMultiply(factor);
  }

  public double distance(Vector v) {
    return Math.sqrt(innerProduct(v));
  }

  @Override
  public String toString() {
    return "<" + x + ", " + y + ">";
  }

}
