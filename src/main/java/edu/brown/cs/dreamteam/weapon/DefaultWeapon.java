package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;

/**
 * 
 * @author peter
 *
 */
public class DefaultWeapon extends Weapon {

  private BoxSet hitBox;
  private Vector hitBoxOffset;

  private double radius;

  private double baseDamage;

  public DefaultWeapon() {
    init();
  }

  private void init() {
    radius = 0;
    hitBox = new BoxSet(radius);
    hitBoxOffset = new Vector(0, 0);
  }

  @Override
  public void fire() {
    return;
  }

  @Override
  public boolean canStartAttack() {
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

  @Override
  public void hit(HurtBoxed hurtBoxed) {
    hurtBoxed.getHit(this);
  }

  @Override
  public double baseDamage() {
    return baseDamage;
  }

}
