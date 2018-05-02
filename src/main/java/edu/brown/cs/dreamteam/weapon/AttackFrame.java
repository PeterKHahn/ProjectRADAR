package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;

public abstract class AttackFrame implements HitBoxed {

  private final int duration;
  private final double damage;

  private final BoxSet hitbox;

  public AttackFrame(int duration, double damage, BoxSet hitbox) {
    this.duration = duration;
    this.damage = damage;
    this.hitbox = hitbox;

  }

  public double duration() {
    return duration;
  }

  @Override
  public BoxSet hitBox() {
    return hitbox;
  }

  @Override
  public double baseDamage() {
    return damage;
  }

}
