package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.TwoDVector;
import edu.brown.cs.dreamteam.utility.Geometry2D;

/**
 * A CircleBox is a box that is dynamic, and is defined by a point and a radius.
 * Circleboxes are primarily used for player hurt and collision boxes.
 *
 * @author peter
 *
 */
public class CircleBox extends Box {

  private double radius;
  private double x;
  private double y;

  /**
   * Constructs a CircleBox given a center and a radius. The TwoDVector, center,
   * passed in is not used in the circle box. Changing this vector will not
   * change the location of the circlebox.
   *
   * @param center
   *          The initial center of the CircleBox
   * @param radius
   *          the Radius of the CircleBox
   */
  public CircleBox(TwoDVector center, double radius) {
    this.x = center.getX();
    this.y = center.getY();
    this.radius = radius;
  }

  /**
   * Constructs a circleBox using x, y
   * 
   * @param x
   * @param y
   * @param radius
   */
  public CircleBox(double x, double y, double radius) {
    this.radius = radius;
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean collides(RectangleBox box) {
    return box.distanceToRectangle(x, y) <= radius;
  }

  @Override
  public boolean collides(CircleBox box) {

    double minDistance = box.getRadius() + this.getRadius();
    return Geometry2D.distance(this.x, this.y, box.x, box.y) <= minDistance;

  }

  /**
   * Returns the radius.
   *
   * @return the radius.
   */
  public double getRadius() {
    return radius;
  }

  /**
   * Returns the x position
   *
   * @return the x position
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y Position
   * 
   * @return the y position
   */
  public double getY() {
    return y;
  }

  /**
   * Updates the position of the circlebox to xPos, yPos
   *
   * @param xPos
   *          The x position to set the circlebox
   * @param yPos
   *          the y position to set the circlebox
   */
  public void updatePosition(double xPos, double yPos) {
    updateX(xPos);
    updateY(yPos);

  }

  /**
   * Updates the xValue to xPos
   * 
   * @param xPos
   *          the x position to set the circlebox
   */
  public void updateX(double xPos) {
    this.x = xPos;
  }

  /**
   * Updates the y value to yPos
   * 
   * @param yPos
   *          the y position to set the circlebox
   */
  public void updateY(double yPos) {
    this.y = yPos;
  }

  @Override
  public double getUpper() {
    return y + radius;
  }

  @Override
  public double getLower() {
    return y - radius;
  }

  @Override
  public double getLeft() {
    return x - radius;
  }

  @Override
  public double getRight() {
    return x + radius;
  }

}
