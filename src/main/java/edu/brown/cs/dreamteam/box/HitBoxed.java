package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.datastructures.Vector;

public interface HitBoxed {

  public boolean isHitboxActive();

  public BoxSet hitBox();

  public Vector hitBoxOffset();

  public void hit(HurtBoxed hurtBoxed);

  public double baseDamage();

}
