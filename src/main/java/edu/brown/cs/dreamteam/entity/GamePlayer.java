package edu.brown.cs.dreamteam.entity;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.Inventory;

/**
 * The internal representation of a player in the Game.
 * 
 * @author peter
 *
 */
public class GamePlayer extends DynamicEntity implements HitBoxed {

  private static final int size = 5;

  private boolean itemPickedFlag;
  private Set<Integer> itemsDropped;
  private boolean primaryActionFlag;

  private boolean isAlive;

  private Inventory inventory;

  public static GamePlayer player(String sessionId, double xpos, double ypos) {
    return new GamePlayer(sessionId, xpos, ypos);
  }

  /**
   * Constructor for GamePlayer that initializes its origin position as well as
   * its id.
   * 
   * @param id
   *          the unique ID of the player
   * @param xPos
   *          the x position to start
   * @param yPos
   *          the y position to start
   */
  private GamePlayer(String id, double xpos, double ypos) {
    super(id, xpos, ypos, size);
    init();

  }

  private void init() {
    itemPickedFlag = false;
    itemsDropped = new HashSet<Integer>();
    primaryActionFlag = false;
    isAlive = true;
    inventory = new Inventory();
  }

  /**
   * Returns during a given tick if the player should pick up an item.
   * 
   * @return true if the player should pick up items, false otherwise
   */
  public boolean itemPickedFlag() {
    return itemPickedFlag;
  }

  /**
   * Returns during a given tick if the player should perform its primary
   * action.
   * 
   * @return True if the player should perform their primary action, false
   *         otherwise
   */
  public boolean primaryActionFlag() {
    return primaryActionFlag;

  }

  /**
   * Returns during a given tick the set of items the player should drop.
   * 
   * @return The set of items the player should drop
   */
  public Set<Integer> itemsDroppedFlag() {
    return itemsDropped;
  }

  /**
   * Given a ClientState, updates the internal representations of the GamePlayer
   * to match the state.
   * 
   * @param state
   *          the ClientState to match
   */
  public void update(ClientState state) {
    int horzCoeff = state.retrieveHorzMultiplier();
    int vertCoeff = state.retrieveVertMultiplier();

    updatePlayer(state);
    updateDynamic(vertCoeff, horzCoeff);
  }

  /**
   * Initializes the player flags given the ClientState.
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
   * Returns if the player is alive at any given point.
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
  public void tick(ChunkMap chunkMap) {
    updatePosition(chunkMap); // Calls movement in dynamic entity
  }

  @Override
  public boolean isActive() {
    return inventory.getActiveWeapon().isActive();
  }

  @Override
  public BoxSet hitBox() {
    return inventory.getActiveWeapon().hitBox();

  }

  @Override
  public Vector collisionBoxOffset() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Vector hitBoxOffset() {
    // TODO Auto-generated method stub
    return null;
  }

}
