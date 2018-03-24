package edu.brown.cs.dreamteam.event;

import java.util.HashSet;
import java.util.Set;

public class ClientState {

  private boolean leftHeld;
  private boolean rightHeld;
  private boolean forwardHeld;
  private boolean backwardHeld;

  private boolean itemPicked;
  private boolean primaryAction;

  private double theta;
  private Set<Integer> dropped;

  private String id;

  public ClientState(String id) {
    init();
  }

  private synchronized void init() {
    leftHeld = false;
    rightHeld = false;
    forwardHeld = false;
    backwardHeld = false;
    itemPicked = false;
    primaryAction = false;
    theta = Math.PI / 2;
    dropped = new HashSet<Integer>();
  }

  public synchronized void itemDropped(int index) {
    dropped.add(index);
  }

  public synchronized void forwardHeld(boolean held) {
    forwardHeld = held;
  }

  public synchronized void backwardHeld(boolean held) {
    backwardHeld = held;
  }

  public synchronized void leftHeld(boolean held) {
    leftHeld = held;
  }

  public synchronized void rightHeld(boolean held) {
    rightHeld = held;
  }

  /**
   * Theta should be relative to the positive x axis.
   *
   * @param angle
   */
  public synchronized void angleSet(double angle) {
    theta = angle;
  }

  public synchronized void itemPicked() {
    itemPicked = true;
  }

  public synchronized void primaryAction() {
    primaryAction = true;
  }

  public synchronized boolean retrievePrimaryAction() {
    boolean res = primaryAction;
    primaryAction = false;
    return res;
  }

  public synchronized boolean retrieveItemPicked() {
    boolean res = itemPicked;
    itemPicked = false;
    return res;
  }

  public synchronized int retrieveStrafeMultiplier() {
    if (leftHeld == rightHeld) {
      return 0;
    } else {
      return leftHeld ? -1 : 1;
    }
  }

  public synchronized int retrieveMovementMultiplier() {
    if (forwardHeld == backwardHeld) {
      return 0;
    } else {
      return backwardHeld ? -1 : 1;
    }
  }

  public synchronized double retrieveTheta() {
    return theta;
  }

  public synchronized Set<Integer> retrieveItemsDropped() {
    Set<Integer> res = dropped;
    dropped = new HashSet<Integer>();
    return res;

  }

  public String getClientId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ClientState) {
      ClientState c = (ClientState) o;
      return this.getClientId() == c.getClientId();
    }
    return false;
  }

}
