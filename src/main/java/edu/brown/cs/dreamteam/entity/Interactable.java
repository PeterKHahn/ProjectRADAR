package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.box.Reach;

public abstract class Interactable extends Entity
    implements Reach, HitBoxed, HurtBoxed, CollisionBoxed {

  public Interactable(String id) {
    super(id);
  }

  public abstract String getDrawType();

  public abstract void hit(Interactable e);

  public abstract boolean hits(Interactable e);

}
