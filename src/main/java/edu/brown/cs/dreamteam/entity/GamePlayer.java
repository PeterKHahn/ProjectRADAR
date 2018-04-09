package edu.brown.cs.dreamteam.entity;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.event.ClientState;

/**
 * The internal representation of a player in the Game
 * 
 * @author peter
 *
 */
public class GamePlayer extends DynamicEntity {

  private static final int size = 5;

  private boolean itemPickedFlag;
  private Set<Integer> itemsDropped;
  private boolean primaryActionFlag;

  private boolean isAlive;

  /**
   * Constructor for GamePlayer that initializes its origin position as well as
   * its id
   * 
   * @param id
   *          the unique ID of the player
   * @param xPos
   *          the x position to start
   * @param yPos
   *          the y position to start
   */
  public GamePlayer(String id, double xPos, double yPos) {
    super(id, xPos, yPos, size);
    init();

  }

  private void init() {
    itemPickedFlag = false;
    itemsDropped = new HashSet<Integer>();
    primaryActionFlag = false;
    isAlive = true;
  }

  /**
   * Returns during a given tick if the player should pick up an item
   * 
   * @return true if the player should pick up items, false otherwise
   */
  public boolean itemPickedFlag() {
    return itemPickedFlag;
  }

  /**
   * Returns during a given tick if the player should perform its primary action
   * 
   * @return True if the player should perform their primary action, false
   *         otherwise
   */
  public boolean primaryActionFlag() {
    return primaryActionFlag;

  }

  /**
   * Returns during a given tick the set of items the player should drop
   * 
   * @return The set of items the player should drop
   */
  public Set<Integer> itemsDroppedFlag() {
    return itemsDropped;
  }

  /**
   * Given a ClientState, updates the internal representations of the GamePlayer
   * to match the state
   * 
   * @param state
   *          the ClientState to match
   */
  public void update(ClientState state) {
    updatePlayer(state);
    updateDynamic(state);
  }

  /**
   * Initializes the player flags given the ClientState
   * 
   * @param state
   *          The ClientState to match
   */
  private void updatePlayer(ClientState state) {
    itemPickedFlag = state.retrieveItemPicked();
    primaryActionFlag = state.retrievePrimaryAction();
    itemsDropped = state.retrieveItemsDropped();
  }

  /**
   * Returns if the player is alive at any given point
   * 
   * @return true if the player is alive, false otherwise
   */
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public void kill() {
    isAlive = false;
  }

  @Override
  public void tick() {

  }

}
