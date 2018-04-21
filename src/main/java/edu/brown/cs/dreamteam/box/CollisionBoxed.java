package edu.brown.cs.dreamteam.box;

public interface CollisionBoxed {

  public boolean isSolid();

  public BoxSet collisionBox();

  public default Point getCenter() {
    return collisionBox().getCenter();
  }

}
