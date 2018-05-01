package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.utility.Logger;

public class EnergyBlast extends Weapon {

  private static final double BASE_DAMAGE = 10;

  private Attack attack;

  public EnergyBlast() {
    init();
  }

  private void init() {
    attack = Attack.create().addCircle(6, BASE_DAMAGE, 5).addInactive(5)
        .build();
  }

  @Override
  public void tick() {
    attack.tick();
  }

  @Override
  public void fire() {
    Logger.logDebug("Firing...");
    if (canStartAttack()) {
      Logger.logDebug("Free to start attack");
      attack.attack();
    }

  }

  @Override
  public boolean canStartAttack() {
    return attack.canStartAttack();
  }

  @Override
  public boolean isHitboxActive() {
    return attack.isHitboxActive();
  }

  @Override
  public BoxSet hitBox() {
    return attack.hitBox();
  }

  @Override
  public Vector hitBoxOffset() {
    return attack.hitBoxOffset();
  }

  @Override
  public void hit(HurtBoxed hurtBoxed) {
    attack.hit(hurtBoxed);

  }

  @Override
  public double baseDamage() {
    return attack.baseDamage();
  }

}