package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

/**
 * A default weapon is never active.
 * 
 * @author peter
 *
 */
public class DefaultWeapon extends Weapon {

  private BoxSet hitBox;
  private Vector hitBoxOffset;

  private double radius;

  public DefaultWeapon() {
    init();
  }

  private void init() {
    radius = 0;
    hitBox = new BoxSet(new Box(radius));
    hitBoxOffset = new Vector(0, 0);
  }

  @Override
  public void fire() {

  }

  @Override
  public boolean canFire() {
    return false;
  }

  @Override
  public void tick() {

  }

  @Override
  public boolean isHitboxActive() {
    return false;
  }

  @Override
  public BoxSet hitBox() {
    return hitBox;
  }

  @Override
  public Vector hitBoxOffset() {
    return hitBoxOffset;
  }

}
