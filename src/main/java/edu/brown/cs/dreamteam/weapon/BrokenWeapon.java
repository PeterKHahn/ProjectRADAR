package edu.brown.cs.dreamteam.weapon;

public class BrokenWeapon extends Weapon {
  public BrokenWeapon() {
    attack = Attack.create().addInactive(60).addCircle(120, 120, 300).build();
  }

  @Override
  public String getName() {
    return "lol gg";
  }

}
