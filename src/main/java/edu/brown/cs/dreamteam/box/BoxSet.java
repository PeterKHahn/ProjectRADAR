package edu.brown.cs.dreamteam.box;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.utility.Geometry2D;

public class BoxSet implements Reach {

  private Collection<Box> boxes;

  private double reach;
  private Point center;

  /**
   * The BoxSet constructor that is just a box.
   * 
   * @param box
   *          The box that is the BoxSet
   */
  public BoxSet(Box box) {
    init();
    boxes.add(box);
    reach = box.radius();
    this.center = box.center();
  }

  /**
   * Box Set constructor, initializing with a collection of boxes.
   * 
   * @param center
   *          the center of the BoxSet
   * @param boxes
   *          the boxes that make up the box Set
   */
  public BoxSet(Point center, Collection<Box> boxes) {
    this.boxes = boxes;
    this.center = center;
    for (Box box : boxes) {
      double tmp = Geometry2D.distance(center, box.center()) + box.radius();
      reach = Math.max(reach, tmp);
    }
  }

  private void init() {
    boxes = new LinkedList<Box>();
  }

  public Collection<Box> boxes() {
    return boxes;
  }

  /**
   * Returns if the BoxSet collides with another BoxSet.
   *
   * @param boxSet
   *          The BoxSet we are colliding against
   * @return whether the boxes collide
   */
  public boolean collides(BoxSet boxSet) {
    for (Box box : boxes()) {
      for (Box box2 : boxSet.boxes()) {
        if (box.collides(box2)) {
          return true;
        }
      }
    }
    return false;
  }

  public Point getCenter() {
    return center;
  }

  @Override
  public double reach() {
    return reach;
  }

  /**
   * Moves the Box Set in the direction and magnitude of Vector v.
   * 
   * @param v
   *          the vector that dictates the movement of the BoxSet
   */
  public void move(Vector v) {
    center = center.move(v);
    for (Box b : boxes) {
      b.move(v);
    }
  }

  @Override
  public Point center() {
    return center;
  }

}
