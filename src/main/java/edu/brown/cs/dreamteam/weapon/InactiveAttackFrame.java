package edu.brown.cs.dreamteam.weapon;

public class InactiveAttackFrame extends AttackFrame {

  public InactiveAttackFrame(int duration) {
    super(duration, 0, false, null, null);
  }

  public InactiveAttackFrame() {
    super(0, 0, false, null, null);
  }

}
