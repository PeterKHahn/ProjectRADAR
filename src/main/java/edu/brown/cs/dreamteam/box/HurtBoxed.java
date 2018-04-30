package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public interface HurtBoxed {
  public BoxSet hurtBox();

  public Vector hurtBoxOffset();

  public void getHit(HitBoxed hitBoxed);

}
