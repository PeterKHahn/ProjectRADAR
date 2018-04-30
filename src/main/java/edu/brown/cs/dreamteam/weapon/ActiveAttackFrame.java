package edu.brown.cs.dreamteam.weapon;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;

public class ActiveAttackFrame extends AttackFrame {

  public ActiveAttackFrame(int duration, double damage, BoxSet hitbox,
      Vector vector) {
    super(duration, damage, true, hitbox, vector);
  }

}
