package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public interface HitBoxed {

  public boolean isActive();

  public BoxSet hitBox();

  public Vector hitBoxOffset();

}
