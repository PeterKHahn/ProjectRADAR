package edu.brown.cs.dreamteam.utility;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class DreamMath {

  /**
   * Finds the max of all the numbers.
   * 
   * @param first
   *          the first number
   * @param nums
   *          the rest of the numbers
   * @return the maximum of the list
   */
  public static double max(double first, double... nums) {

    double soFar = first;
    for (double n : nums) {
      soFar = Math.max(soFar, n);
    }

    return soFar;
  }

  public static double quadratic(double a, double b, double c,
      boolean minimal) {
    double discriminant = b * b - 4 * a * c;
    if (discriminant < 0) {
      Logger.logError("DISCRIMINANT < 0 FUCK");
      return 0;
    }

    double res1 = (-b + Math.sqrt(discriminant)) / (2 * a);
    double res2 = (-b - Math.sqrt(discriminant)) / (2 * a);

    return minimal ? Math.min(res1, res2) : Math.max(res1, res2);
  }

  /**
   * Checks whether two line segments intersect. pos1 is the starting point of
   * the first line segment which has the direction of vector a and ends at
   * (pos1.x + a1.x, pos1.y + a1.y). Similarly, pos2 is the starting point of
   * the second line segment and it ends at (pos2.x + a2.x, pos2.y + a2.y).
   * 
   * @return True if the line segments intersect, false otherwise.
   */
  public static boolean doIntersect(Vector pos1, Vector a1, Vector pos2,
      Vector a2) {
    if (a1.cross(a2) == 0) {
      if (pos2.subtract(pos1).cross(a1) == 0) {
        return true;
      }
      return false;
    }

    double t = pos2.subtract(pos1).cross(a2) / a1.cross(a2);
    double u = pos2.subtract(pos1).cross(a1) / a1.cross(a2);
    if (Double.compare(t, 0) != 1 && Double.compare(t, -1) != -1
        && Double.compare(u, 0) != 1 && Double.compare(u, -1) != -1) {
      return true;
    }
    return false;
  }

}
