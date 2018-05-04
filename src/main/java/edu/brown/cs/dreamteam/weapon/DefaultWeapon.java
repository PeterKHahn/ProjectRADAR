package edu.brown.cs.dreamteam.weapon;

/**
 * 
 * @author peter
 *
 */
public class DefaultWeapon extends Weapon {

  public DefaultWeapon() {
    init();
  }

  private void init() {
    attack = Attack.create().addInactive(3).build();
  }

}
