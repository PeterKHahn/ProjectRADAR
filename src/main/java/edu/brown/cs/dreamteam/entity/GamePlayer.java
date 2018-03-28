package edu.brown.cs.dreamteam.entity;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.event.ClientState;

public class GamePlayer extends DynamicEntity {

  private boolean itemPickedFlag;
  private Set<Integer> itemsDropped;
  private boolean primaryActionFlag;

  private boolean isAlive;

  public GamePlayer(String id, double xPos, double yPos) {
    super(id, xPos, yPos);
    init();

  }

  private void init() {
    itemPickedFlag = false;
    itemsDropped = new HashSet<Integer>();
    primaryActionFlag = false;
    isAlive = true;
  }

  public boolean itemPickedFlag() {
    return itemPickedFlag;
  }

  public boolean primaryActionFlag() {
    return primaryActionFlag;

  }

  public Set<Integer> itemsDroppedFlag() {
    return itemsDropped;
  }

  public void update(ClientState state) {
    updatePlayer(state);
    updateDynamic(state);
  }

  private void updatePlayer(ClientState state) {
    itemPickedFlag = state.retrieveItemPicked();
    primaryActionFlag = state.retrievePrimaryAction();
    itemsDropped = state.retrieveItemsDropped();
  }

  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public void kill() {
    isAlive = false;
  }

  @Override
  public void tick() {
    System.out.println("GamePlayer 40: I AM A PLAYER");
    updatePosition();
    System.out.println("xVel: " + getXVelocity() + " yVel: " + getYVelocity());
    System.out.println("xPos: " + getXPos() + " yPos: " + getYPos());

  }

}
