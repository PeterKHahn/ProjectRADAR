package edu.brown.cs.dreamteam.utility;

/**
 * The Clamp class is an abstraction of boundaries. Like a clamp, if something
 * is not in the range it is supposed to be in, the Clamp class can clamp the
 * number so that it falls into the range.
 * 
 * @author peter
 *
 */
public class Clamp {

  private double lesserBound;
  private double greaterBound;

  /**
   * Constructs a Clamp for two given bounds.
   *
   * @param bound1
   *          The first bound
   * @param bound2
   *          The second bound
   */
  public Clamp(double bound1, double bound2) {
    lesserBound = Math.min(bound1, bound2);
    greaterBound = Math.max(bound1, bound2);

  }

  /**
   * Clamps value into the range specified by the constructor. If it falls
   * between the range of the two specified bounds, this method returns value.
   * Otherwise, it returns the bound that is closest to value.
   *
   * @param value
   *          The value being clamped
   * @return value if value falls between the range of the two bounds, otherwise
   *         returns the clamped value.
   */
  public double clamp(double value) {
    if (value < lesserBound) {
      return lesserBound;
    } else if (value > greaterBound) {
      return greaterBound;
    } else {
      return value;
    }
  }

  /**
   * A method to test whether two clamps overlap each other. Overlap is defined
   * by the property that there exists a value such that it falls within both
   * clamps.
   *
   * @param c
   *          The clamp we are testing overlap with
   * @return true if the clamps overlap, false otherwise.
   */
  public boolean overlaps(Clamp c) {
    return this.clamp(c.lesserBound) == c.lesserBound
        || this.clamp(c.greaterBound) == c.greaterBound
        || c.clamp(this.lesserBound) == this.lesserBound
        || c.clamp(this.greaterBound) == this.greaterBound;
  }

  public boolean contains(double value) {
    return clamp(value) == value;
  }

}
