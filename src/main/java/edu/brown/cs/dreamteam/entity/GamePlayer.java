package edu.brown.cs.dreamteam.entity;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.Inventory;
import edu.brown.cs.dreamteam.utility.DreamMath;
import edu.brown.cs.dreamteam.weapon.Weapon;

/**
 * The internal representation of a player in the Game.
 * 
 * @author peter
 *
 */
public class GamePlayer extends DynamicEntity implements HitBoxed, HurtBoxed {

  private static final int SIZE = 5;
  private static final int MAX_HEALTH = 100;

  private boolean itemPickedFlag;
  private Set<Integer> itemsDropped;
  private boolean primaryActionFlag; // whether or not we should fire

  private boolean isAlive;
  private double health;

  private Inventory inventory;

  private Vector collisionBoxOffset;

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
    super(id, xpos, ypos, SIZE);
    init();

  }

  private void init() {
    itemPickedFlag = false;
    itemsDropped = new HashSet<Integer>();
    primaryActionFlag = false;
    isAlive = true;
    inventory = new Inventory();
    collisionBoxOffset = new Vector(0, 0);
    health = MAX_HEALTH;
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
    System.out.println(new Vector(horzCoeff, vertCoeff));
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
    inventory.tick();

    if (primaryActionFlag) {
      inventory.getActiveWeapon().fire();
    }

    // check collision here
    if (isHitboxActive()) {
      // only iterate if it is active
    }

    if (health < 0) {
      this.kill();
    }
  }

  @Override
  public boolean isHitboxActive() {
    return inventory.getActiveWeapon().isHitboxActive();
  }

  @Override
  public BoxSet hitBox() {
    return inventory.getActiveWeapon().hitBox();

  }

  @Override
  public Vector collisionBoxOffset() {
    return collisionBoxOffset;
  }

  @Override
  public Vector hitBoxOffset() {
    return inventory.getActiveWeapon().hitBoxOffset();
  }

  @Override
  public double reach() {
    double tmp = DreamMath.max(
        this.collisionBox().reach() + collisionBoxOffset().magnitude(),
        this.hitBox().reach() + hitBoxOffset().magnitude(), SIZE);
    return tmp + speedCap();
  }

  @Override
  public void hit(HurtBoxed hurtBoxed) {
    Weapon weapon = inventory.getActiveWeapon();
    weapon.hit(hurtBoxed);

  }

  @Override
  public BoxSet hurtBox() {
    return collisionBox();
  }

  @Override
  public Vector hurtBoxOffset() {
    return collisionBoxOffset();
  }

  @Override
  public void getHit(HitBoxed hitBoxed) {
    double damage = hitBoxed.baseDamage();
    health -= damage;

  }

  @Override
  public double baseDamage() {
    return inventory.getActiveWeapon().baseDamage();
  }

}
