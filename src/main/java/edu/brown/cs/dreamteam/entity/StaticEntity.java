package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;

/**
 * A StaticEntity is a Rectangular entity that does not change position.
 * 
 * @author peter
 *
 */
public abstract class StaticEntity extends Entity implements CollisionBoxed {

  private Vector center;
  private double radius;

  public StaticEntity(String id, Vector center, double radius) {
    super(id);
    this.center = center;
    this.radius = radius;
  }

  @Override
  public Vector center() {
    return center;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        "STATIC ENTITY: " + getId() + ", Radius=" + radius + " at " + center);
    return sb.toString();
  }

}
