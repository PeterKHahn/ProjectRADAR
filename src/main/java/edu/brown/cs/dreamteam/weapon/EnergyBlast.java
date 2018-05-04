package edu.brown.cs.dreamteam.weapon;

public class EnergyBlast extends Weapon {

  private static final double BASE_DAMAGE = 10;

  public EnergyBlast() {
    init();
  }

  private void init() {
    attack = Attack.create().addCircle(6, BASE_DAMAGE, 5).addInactive(5)
        .addCircle(6, BASE_DAMAGE * 1.5, 7).addInactive(5).build();
  }

  @Override
  public String getName() {
    return "Energy Blast";
  }

}
