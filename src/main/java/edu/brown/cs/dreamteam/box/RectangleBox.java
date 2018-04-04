package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.TwoDVector;
import edu.brown.cs.dreamteam.utility.Clamp;

/**
 * A class for Rectangle Boxes, that will primarily be used for staticly placed
 * obstacles. Rectangle Boxes have no notion of changing position
 *
 * @author peter
 *
 */
public class RectangleBox extends Box {

  private double upperBound;
  private double lowerBound;
  private double leftBound;
  private double rightBound;

  private Clamp xClamp;
  private Clamp yClamp;

  /**
   * Given two corner vectors, will construct a rectangle that falls within the
   * perpendicular bounds of these two points.
   *
   * @param point1
   *          The first corner of the rectangle
   * @param point2
   *          the second corner of the rectnagle, that in conjunction with
   *          point1, defines the rectangle
   */
  public RectangleBox(TwoDVector point1, TwoDVector point2) {

    this.upperBound = Math.max(point1.getY(), point2.getY());
    this.lowerBound = Math.min(point1.getY(), point2.getY());
    this.leftBound = Math.min(point1.getX(), point2.getX());
    this.rightBound = Math.max(point1.getX(), point2.getX());
    init();

  }

  private void init() {
    xClamp = new Clamp(leftBound, rightBound);
    yClamp = new Clamp(lowerBound, upperBound);
  }

  @Override
  public boolean collides(RectangleBox box) {

    return xClamp.overlaps(box.xClamp) && yClamp.overlaps(box.yClamp);

  }

  /**
   * Returns the xClamp for this Rectangle box, that represents the horizontal
   * bounds of the rectangle box
   *
   * @return the xClamp for this rectangle box.
   */
  public Clamp getXClamp() {
    return xClamp;
  }

  /**
   * Returns the yClapm for this Rectangle Box, that represents the vertical
   * bounds of the rectangle box
   * 
   * @return the yClamp for this rectangle box
   */
  public Clamp getYClamp() {
    return yClamp;
  }

  @Override
  public boolean collides(CircleBox box) {
    double circleX = box.getX();
    double circleY = box.getY();
    return this.distanceToRectangle(circleX, circleY) <= box.getRadius();
  }

  /**
   * Given a point defined by xPos and yPos, will return the shortest distance
   * to the rectangle. This method returns 0 if the point is contained in the
   * rectangle
   *
   * @param xPos
   *          The xPosition of the point in question
   * @param yPos
   *          The yPosition of the point in question
   * @return
   */
  public double distanceToRectangle(double xPos, double yPos) {

    double clampX = xClamp.clamp(xPos);
    double clampY = yClamp.clamp(yPos);

    return Math.sqrt(
        (xPos - clampX) * (xPos - clampX) + (yPos - clampY) * (yPos - clampY));

  }

  /**
   * Returns whether the x value is within the two vertical bounds of the
   * rectangle
   * 
   * @param x
   *          the x value in question
   * @return whether the x value is within the two vertical bounds of the
   *         rectangle
   */
  private boolean isWithinX(double x) {
    return x <= rightBound && x >= leftBound;
  }

  /**
   * Returns whether the y value is within the two horizontal bounds of the
   * rectangle
   * 
   * @param y
   *          the y value in question
   * @return whether the y value is within the two vertical bounds of the
   *         rectangle
   */
  private boolean isWithinY(double y) {
    return y <= upperBound && y >= lowerBound;
  }

  /**
   * Returns if the point specified at (x,y) falls within the rectangle
   * 
   * @param x
   *          the x value in question
   * @param y
   *          the y value in question
   * @return true if the x and y value fall within the rectangle bounds, false
   *         otherwise
   */
  public boolean isInBounds(double x, double y) {
    return isWithinX(x) && isWithinY(y);
  }

  @Override
  public double getUpper() {
    return upperBound;
  }

  @Override
  public double getLower() {
    return lowerBound;
  }

  @Override
  public double getLeft() {
    return leftBound;
  }

  @Override
  public double getRight() {
    return rightBound;
  }

}
