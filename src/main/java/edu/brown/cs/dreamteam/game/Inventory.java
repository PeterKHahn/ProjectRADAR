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
  private int delta = 0;

  public Inventory() {
    init();
  }

  private void init() {
    weapon = new DefaultWeapon();
    radars = new LinkedList<Radar>();
    numRadars = 0;
  }

  public boolean hasWeapon() {
    return !(weapon instanceof DefaultWeapon);

  }

  public Weapon getActiveWeapon() {
    return weapon;
  }

  public void tick() {
    weapon.tick();
    delta++;
  }

  public void addWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public void addRadarPiece() {
    numRadars++;
  }

  public boolean canDropRadar() {
    return numRadars > 0 && delta > 3;
  }

  public void dropRadar(Vector center) {
    if (canDropRadar()) {
      Radar res = new Radar(center);
      numRadars--;
      radars.add(res);
      delta = 0;
    }

  }

}
