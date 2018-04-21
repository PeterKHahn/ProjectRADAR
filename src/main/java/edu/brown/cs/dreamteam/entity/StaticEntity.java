package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.box.Point;

/**
 * A StaticEntity is a Rectangular entity that does not change position.
 * 
 * @author peter
 *
 */
public abstract class StaticEntity extends Entity implements CollisionBoxed {

  private Point center;
  private double radius;

  public StaticEntity(String id, Point center, double radius) {
    super(id);
    this.center = center;
    this.radius = radius;
  }

  @Override
  public Point center() {
    return center;
  }

}
