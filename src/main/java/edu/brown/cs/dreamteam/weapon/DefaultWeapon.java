package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;

/**
 * A default weapon is never active.
 * 
 * @author peter
 *
 */
public class DefaultWeapon extends Weapon {

  private BoxSet hitBox;

  private double radius;

  public DefaultWeapon() {
    init();
  }

  private void init() {
    radius = 0;
    hitBox = new BoxSet(new Box(radius));
  }

  @Override
  public void fire() {

  }

  @Override
  public boolean canFire() {
    return false;
  }

  public void tick() {

  }

  @Override
  public boolean isActive() {
    return false;
  }

  @Override
  public BoxSet hitBox() {
    return hitBox;
  }

}
