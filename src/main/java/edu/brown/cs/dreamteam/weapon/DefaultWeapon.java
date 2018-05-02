package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;

/**
 * 
 * @author peter
 *
 */
public class DefaultWeapon extends Weapon {

  private BoxSet hitBox;

  private double radius;

  private double baseDamage;

  public DefaultWeapon() {
    init();
  }

  private void init() {
    radius = 0;
    hitBox = new BoxSet(radius);
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
  public BoxSet hitBox() {
    return hitBox;
  }

  @Override
  public double baseDamage() {
    return baseDamage;
  }

}
