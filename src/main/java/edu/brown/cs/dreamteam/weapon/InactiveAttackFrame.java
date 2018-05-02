package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;

public class InactiveAttackFrame extends AttackFrame {

  public InactiveAttackFrame(int duration) {
    super(duration, 0, BoxSet.NullBoxSet());
  }

  public InactiveAttackFrame() {
    super(0, 0, BoxSet.NullBoxSet());
  }

}
