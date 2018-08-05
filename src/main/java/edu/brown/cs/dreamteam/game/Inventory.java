package edu.brown.cs.dreamteam.game;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.radar.Radar;
import edu.brown.cs.dreamteam.weapon.DefaultWeapon;
import edu.brown.cs.dreamteam.weapon.Weapon;

import java.util.Collection;
import java.util.LinkedList;

public class Inventory {

    private Weapon weapon;
    private int numRadars;

    private Collection<Radar> radars; // Radars in this sense are radars that are placed on the board map
    private int delta = 0;

    public Inventory() {
        init();
    }

    public Collection<Radar> getRadars() {
        return radars;
    }

    private void init() {
        weapon = new DefaultWeapon();
        radars = new LinkedList<>();
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
