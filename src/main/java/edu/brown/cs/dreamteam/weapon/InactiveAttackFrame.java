package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public class InactiveAttackFrame extends AttackFrame {

  public InactiveAttackFrame(int duration) {
    super(duration, 0, false, new BoxSet(0), new Vector(0, 0));
  }

  public InactiveAttackFrame() {
    super(0, 0, false, new BoxSet(0), new Vector(0, 0));
  }

}
