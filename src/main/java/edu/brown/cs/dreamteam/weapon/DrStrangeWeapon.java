package edu.brown.cs.dreamteam.weapon;

import com.google.common.collect.ImmutableList;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public class DrStrangeWeapon extends Weapon {
  private static final double ATTACK_RADIUS = 3;
  private static final double ROTATION_VECTOR_MAGNITUDE = 15;

  private static final double SOUR_DAMAGE = 5;
  private static final double SWEET_DAMAGE = 10;

  public DrStrangeWeapon() {
    init();
  }

  private void init() {
    Vector upper = new Vector(0, 1);
    Vector lower = new Vector(0, -1);

    Box circle1 = new Box(ATTACK_RADIUS);

    Box circle2 = new Box(ATTACK_RADIUS, upper);
    Box circle3 = new Box(ATTACK_RADIUS, lower);

    Box circle4 = new Box(ATTACK_RADIUS, upper.scalarMultiply(6));
    Box circle5 = new Box(ATTACK_RADIUS, lower.scalarMultiply(6));

    Box circle6 = new Box(ATTACK_RADIUS,
        upper.scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(Math.PI / 4));
    Box circle7 = new Box(ATTACK_RADIUS,
        lower.scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(Math.PI / 4));

    Box circle8 = new Box(ATTACK_RADIUS,
        upper.scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(Math.PI / 2));
    Box circle9 = new Box(ATTACK_RADIUS,
        lower.scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(Math.PI / 2));

    Box circle10 = new Box(ATTACK_RADIUS, upper
        .scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(3 * Math.PI / 4));
    Box circle11 = new Box(ATTACK_RADIUS, lower
        .scalarMultiply(ROTATION_VECTOR_MAGNITUDE).rotate(3 * Math.PI / 4));

    Box circle12 = new Box(ROTATION_VECTOR_MAGNITUDE);

    BoxSet b1 = new BoxSet(circle1);
    BoxSet b2 = new BoxSet(ImmutableList.of(circle2, circle3));
    BoxSet b3 = new BoxSet(ImmutableList.of(circle4, circle5));
    BoxSet b4 = new BoxSet(ImmutableList.of(circle6, circle7));
    BoxSet b5 = new BoxSet(ImmutableList.of(circle8, circle9));
    BoxSet b6 = new BoxSet(ImmutableList.of(circle10, circle11));
    // redo BoxSet b3
    BoxSet b7 = new BoxSet(circle12);

    AttackFrame a1 = new ActiveAttackFrame(6, SOUR_DAMAGE, b1);
    AttackFrame a2 = new ActiveAttackFrame(6, SOUR_DAMAGE, b2);
    AttackFrame a3 = new ActiveAttackFrame(6, SOUR_DAMAGE, b3);
    AttackFrame a4 = new ActiveAttackFrame(2, SOUR_DAMAGE, b4);
    AttackFrame a5 = new ActiveAttackFrame(2, SOUR_DAMAGE, b5);
    AttackFrame a6 = new ActiveAttackFrame(2, SOUR_DAMAGE, b6);
    // redo a3
    AttackFrame a7 = new ActiveAttackFrame(7, SWEET_DAMAGE, b7);

    attack = Attack.create().addAttackFrame(a1).addAttackFrame(a2)
        .addAttackFrame(a3).addAttackFrame(a4).addAttackFrame(a5)
        .addAttackFrame(a6).addAttackFrame(a3).addAttackFrame(a7).build();

  }
}
