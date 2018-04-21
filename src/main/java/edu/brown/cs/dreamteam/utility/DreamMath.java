package edu.brown.cs.dreamteam.utility;

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

}
