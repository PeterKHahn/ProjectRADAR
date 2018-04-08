package edu.brown.cs.dreamteam.box;

/**
 * Boxed is an interface for finding the effective bounds of a given object at
 * any given instance of time. Given a position in the plane, the bounds must
 * cover the perpendicular range of effect that the given entity can take. If
 * for example, an entity were to have a radius of 5 and a velocity of 5, the
 * bounding boxes must take into account the set of locations the entity can
 * effect in a single frame, namely 10 units on all sides.
 * 
 * @author peter
 *
 */
public interface Boxed {

  /**
   * Returns the upper bound of the boxed object.
   *
   * @return The upper bound of the boxed object
   */
  public abstract double getUpper();

  /**
   * Returns the lower bound of the boxed object.
   *
   * @return The lower bound of the boxed object
   */
  public abstract double getLower();

  /**
   * Returns the leftmost bound of the boxed object.
   *
   * @return The leftmost bound of the boxed object
   */
  public abstract double getLeft();

  /**
   * Returns the rightmost bound of the boxed object.
   *
   * @return The rightmost bound of the boxed object
   */
  public abstract double getRight();

}
