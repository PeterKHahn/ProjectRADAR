package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.LinkedList;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.radar.Radar;
import edu.brown.cs.dreamteam.weapon.DefaultWeapon;
import edu.brown.cs.dreamteam.weapon.Weapon;

public class Inventory {

  private Weapon weapon;
  private int numRadars;

  private Collection<Radar> radars;

  public Inventory() {
    init();
  }

  private void init() {
    weapon = new DefaultWeapon();
    radars = new LinkedList<Radar>();
    numRadars = 0;
  }

  public Weapon getActiveWeapon() {
    return weapon;
  }

  public void tick() {
    weapon.tick();
  }

  public void addWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public void addRadarPiece() {
    numRadars++;
  }

  public void dropRadar(Vector center) {
    if (numRadars > 0) {
      Radar res = new Radar(numRadars, center);
      numRadars = 0;
      radars.add(res);
    }

  }

}
