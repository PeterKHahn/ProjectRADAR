package edu.brown.cs.dreamteam.utility;

import edu.brown.cs.dreamteam.box.Point;

public class Geometry2D {

  private Geometry2D() {

  }

  /**
   * Calculates the euclidean distance between two points.
   *
   * @param x1
   *          x coordinate of first point
   * @param y1
   *          y coordinate of first point
   * @param x2
   *          x coordinate of first point
   * @param y2
   *          y coordinate of first point
   * @return
   */
  public static double distance(double x1, double y1, double x2, double y2) {

    double dx = x1 - x2;
    double dy = y1 - y2;

    return Math.sqrt(dx * dx + dy * dy);

  }

  public static boolean counterClockWise(Point p1, Point p2, Point p3) {
    double res = (p2.x - p1.x) * (p2.y + p1.y) + (p3.x - p2.x) * (p3.y + p2.y);
    return res < 0;
  }

}
