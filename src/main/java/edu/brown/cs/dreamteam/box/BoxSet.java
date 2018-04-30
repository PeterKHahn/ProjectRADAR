package edu.brown.cs.dreamteam.box;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class BoxSet implements Reach {

  private final Map<Box, Vector> boxes;

  private final double reach;

  /**
   * The BoxSet constructor that is just a box.
   * 
   * @param box
   *          The box that is the BoxSet
   */
  public BoxSet(double radius) {
    init();
    boxes = new HashMap<Box, Vector>();
    boxes.put(new Box(radius), new Vector(0, 0));

    reach = radius;
  }

  /**
   * Box Set constructor, initializing with a collection of boxes.
   * 
   * @param boxes
   *          the boxes that make up the box Set
   */
  public BoxSet(Map<Box, Vector> boxes) {
    this.boxes = boxes;
    double reach = 0;
    for (Entry<Box, Vector> box : boxes.entrySet()) {
      double tmp = box.getValue().magnitude() + box.getKey().radius();
      reach = Math.max(reach, tmp);
    }
    this.reach = reach;
  }

  private void init() {

  }

  public Map<Box, Vector> boxes() {
    return boxes;
  }

  /**
   * Returns if the BoxSet collides with another BoxSet.
   *
   * @param boxSet
   *          The BoxSet we are colliding against
   * @return whether the boxes collide
   */
  public boolean collides(Vector center1, Vector center2, BoxSet boxSet) {

    for (Entry<Box, Vector> boxEntry : boxes.entrySet()) {
      for (Entry<Box, Vector> boxEntry2 : boxSet.boxes().entrySet()) {
        Vector p1 = center1.add(boxEntry.getValue());
        Vector p2 = center2.add(boxEntry2.getValue());
        if (boxEntry.getKey().collides(p1, p2, boxEntry2.getKey())) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public double reach() {
    return reach;
  }

}
