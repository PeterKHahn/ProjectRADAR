package edu.brown.cs.dreamteam.weapon;

import com.google.common.collect.ImmutableList;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public class ClapWeapon extends Weapon {

  private static final double SOUR_DAMAGE = 5;
  private static final double SWEET_DAMAGE = 10;
  private static final double ATTACK_RADIUS = 5;

  public ClapWeapon() {
    init();
  }

  private void init() {
    Vector right = new Vector(16, 0);
    Vector left = new Vector(-16, 0);
    Box circle1 = new Box(ATTACK_RADIUS, right);
    Box circle2 = new Box(ATTACK_RADIUS, left);
    BoxSet b1 = new BoxSet(ImmutableList.of(circle1, circle2));

    Box circle3 = new Box(ATTACK_RADIUS, right.rotate(Math.PI / 6));
    Box circle4 = new Box(ATTACK_RADIUS, left.rotate(-Math.PI / 6));
    BoxSet b2 = new BoxSet(ImmutableList.of(circle3, circle4));

    Box circle5 = new Box(ATTACK_RADIUS, right.rotate(Math.PI / 3));
    Box circle6 = new Box(ATTACK_RADIUS, left.rotate(-Math.PI / 3));
    BoxSet b3 = new BoxSet(ImmutableList.of(circle5, circle6));

    Box circle7 = new Box(ATTACK_RADIUS, right.rotate(Math.PI / 2));
    BoxSet b4 = new BoxSet(circle7);

    AttackFrame a1 = new ActiveAttackFrame(1, SOUR_DAMAGE, b1);
    AttackFrame a2 = new ActiveAttackFrame(1, SOUR_DAMAGE, b2);
    AttackFrame a3 = new ActiveAttackFrame(1, SOUR_DAMAGE, b3);
    AttackFrame a4 = new ActiveAttackFrame(4, SWEET_DAMAGE, b4);

    attack = Attack.create().addAttackFrame(a1).addAttackFrame(a2)
        .addAttackFrame(a3).addAttackFrame(a4).addInactive(3).build();
  }

}
