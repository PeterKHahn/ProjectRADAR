package edu.brown.cs.dreamteam.radar;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.item.KeyItem;

public class Radar {

  private Vector center;

  public Radar(Vector center) {
    this.center = center;
  }

  public Vector center() {
    return center;
  }

  public double distance(KeyItem item) {
    return item.center().distance(this.center);

  }

}
