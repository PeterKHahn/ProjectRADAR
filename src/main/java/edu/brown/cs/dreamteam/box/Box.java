package edu.brown.cs.dreamteam.box;

/**
 * An abstraction of a box, which can be used for bounding boxes, hitboxes, etc.
 * Its primary purpose is to determine whether or not it collides with another
 * box.
 *
 * @author peter
 *
 */
public abstract class Box {

  /**
   * A method to determine if a given box collides with a Rectangle box.
   * Collision is defined by the existence of a point that falls within the
   * bounds of both boxes.
   *
   * @param box
   *          The RectangleBox we are determining collision with
   * @return true if this and box collide, false otherwise
   */
  public abstract boolean collides(RectangleBox box);

  /**
   * A method to determine if a given box colliders with a CircleBox. Collision
   * is defined by the existence of a point that falls within the bounds of both
   * boxes.
   *
   * @param box
   *          The CircleBox we are determining collision with
   * @return true if this and box collide, false otherwise
   */
  public abstract boolean collides(CircleBox box);

  /**
   * Returns the uppermost value that the box contains.
   *
   * @return the uppermost value that the box contains
   */
  public abstract double getUpper();

  /**
   * Returns the lowermost value that the box contains.
   *
   * @return The lowermost value that the box contains
   */
  public abstract double getLower();

  /**
   * Returns the leftmost value that the box contains.
   *
   * @return the lowermost value that the box contains
   */
  public abstract double getLeft();

  /**
   * Returns the rightmost value that the box contains.
   *
   * @return the rightmost value that the box contains.
   */
  public abstract double getRight();

}
