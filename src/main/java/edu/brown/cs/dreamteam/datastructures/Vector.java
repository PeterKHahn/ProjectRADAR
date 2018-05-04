package edu.brown.cs.dreamteam.datastructures;

import java.util.Objects;

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
    return new Vector(this.x - v.x, this.y - v.y);
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
    return this.subtract(v).magnitude();
  }

  // 2D cross product
  public double cross(Vector v) {
    return this.x * v.y - this.y * v.x;
  }

  public Vector rotate(double theta) {
    double x1 = this.x * Math.cos(theta) - this.y * Math.sin(theta);
    double y1 = this.x * Math.sin(theta) + this.y * Math.cos(theta);
    return new Vector(x1, y1);
  }

  // The angle between this Vector and the given Vector
  public double angle(Vector v) {
    return Math.acos(innerProduct(v) / (magnitude() * v.magnitude()));
  }

  @Override
  public String toString() {
    return "<" + x + ", " + y + ">";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Vector)) {
      return false;
    }

    Vector other = (Vector) o;
    if (Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

}
