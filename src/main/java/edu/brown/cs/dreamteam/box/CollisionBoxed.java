package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public interface CollisionBoxed {

  public boolean isSolid();

  public BoxSet collisionBox();

  public Vector collisionBoxOffset();

  public Vector center();
}
