package edu.brown.cs.dreamteam.event;

import java.util.HashSet;
import java.util.Set;

/**
 * The ClientState class represents all the possible changes in state a Client
 * could have given from the frontend. This will be used to update the game's
 * internal representation of a Player
 * 
 * @author peter
 *
 */
public class ClientState {

  private boolean leftHeld;
  private boolean rightHeld;
  private boolean forwardHeld;
  private boolean backwardHeld;
  private boolean radar;
  private boolean itemPicked;
  private boolean primaryAction;

  private Set<Integer> dropped;

  private String id;

  /**
   * A ClientState constructor that matches its id with that of the player it is
   * interacting with.
   * 
   * @param id
   *          The ID of the ClientState, and that of the player which it
   *          represents
   */
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
    radar = false;
    dropped = new HashSet<Integer>();
  }

  /**
   * Updates the list of dropped items to include the item at index index.
   * 
   * @param index
   *          The index of the newly dropped item
   */
  public synchronized void itemDropped(int index) {
    dropped.add(index);
  }

  /**
   * Updates whether the player is holding forward.
   * 
   * @param held
   *          whether the player is holding forward
   */
  public synchronized void forwardHeld(boolean held) {
    forwardHeld = held;
  }

  /**
   * Updates whether the player is holding backward.
   * 
   * @param held
   *          whether the player is holding backward
   */
  public synchronized void backwardHeld(boolean held) {
    backwardHeld = held;
  }

  /**
   * Updates whether the player is holding left.
   * 
   * @param held
   *          whether the player is holding left
   */
  public synchronized void leftHeld(boolean held) {
    leftHeld = held;
  }

  /**
   * Updates whether the player is holding right.
   * 
   * @param held
   *          whether the player is holding right
   */
  public synchronized void rightHeld(boolean held) {
    rightHeld = held;
  }

  /**
   * updates if the player should pick up an item.
   */
  public synchronized void itemPicked(Boolean item) {
    itemPicked = item;
  }

  public synchronized void placeRadar(Boolean radar) {
    this.radar = radar;
  }

  /**
   * Updates if the player should perform its primary action.
   */
  public synchronized void primaryAction(Boolean action) {
    primaryAction = action;
  }

  /**
   * Returns whether the player should perform its primary action.
   * 
   * @return whether the player should perform its primary action
   */
  public synchronized boolean retrievePrimaryAction() {
    boolean res = primaryAction;
    primaryAction = false;
    return res;
  }

  /**
   * Returns whether the player should pick up items.
   * 
   * @return whether the player should pick up items
   */
  public synchronized boolean retrieveItemPicked() {
    boolean res = itemPicked;
    itemPicked = false;
    return res;
  }

  /**
   * Returns the strafe direction of the player.
   * 
   * @return -1 if only left is hold, 1 if only right is held, 0 if both or
   *         neither is held
   */
  public synchronized int retrieveHorzMultiplier() {
    int res;
    if (leftHeld == rightHeld) {
      res = 0;
    } else {
      res = leftHeld ? -1 : 1;
    }

    return res;
  }

  /**
   * Returns the Movement direction of the player.
   * 
   * @return -1 if only back is held, 1 if only forward is held, 0 if both or
   *         neither is held
   */
  public synchronized int retrieveVertMultiplier() {
    int res;
    if (forwardHeld == backwardHeld) {
      res = 0;
    } else {
      res = backwardHeld ? -1 : 1;
    }

    return res;
  }

  /**
   * Returns the set of indexes of items dropped.
   * 
   * @return the set of indexes of items dropped
   */
  public synchronized Set<Integer> retrieveItemsDropped() {
    Set<Integer> res = dropped;
    dropped = new HashSet<Integer>();
    return res;

  }

  public synchronized boolean retrieveRadar() {
    boolean res = radar;
    radar = false;
    return res;
  }

  /**
   * Returns the client id.
   * 
   * @return
   */
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
