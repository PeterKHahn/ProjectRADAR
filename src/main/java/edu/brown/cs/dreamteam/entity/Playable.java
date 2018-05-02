package edu.brown.cs.dreamteam.entity;

import java.util.Collection;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.Inventory;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.utility.DreamMath;
import edu.brown.cs.dreamteam.utility.Logger;

public abstract class Playable extends DynamicEntity {

  protected boolean itemPickedFlag;
  protected boolean primaryActionFlag; // whether or not we should fire

  protected boolean isAlive;
  protected double health;

  protected Inventory inventory;

  private String type;

  public static final double VISIBLE_RANGE = 10;
  public static final double SIZE = 5;
  public static final int MAX_HEALTH = 100;
  public static final int ITEM_PICK_RANGE = 3;

  public Playable(String id, double x, double y, String type) {
    super(id, x, y, SIZE);
    this.type = type;
    init();
  }

  private void init() {
    itemPickedFlag = false;
    primaryActionFlag = false;
    isAlive = true;
    inventory = new Inventory();
    health = MAX_HEALTH;
  }

  public String getType() {
    return type;
  }

  @Override
  public BoxSet hurtBox() {
    return collisionBox();
  }

  @Override
  public double baseDamage() {
    return inventory.getActiveWeapon().baseDamage();
  }

  @Override
  public void hit(Interactable e) {

    e.getHit(this);
  }

  @Override
  public boolean hits(Interactable hurtBoxed) {

    for (Box b : hitBox().boxes()) {
      for (Box hb : hurtBoxed.hurtBox().boxes()) {
        Vector center = center().add(b.offset());
        Vector center2 = hurtBoxed.center().add(hb.offset());
        double diff = center.subtract(center2).magnitude();
        if (diff < b.radius() + hb.radius()) {
          return true;
        }

      }
    }
    return false;
  }

  @Override
  public void getHit(HitBoxed hitBoxed) {
    double damage = hitBoxed.baseDamage();
    health -= damage;
    if (health < 0) {
      kill();
    }

  }

  @Override
  public BoxSet hitBox() {
    return inventory.getActiveWeapon().hitBox();

  }

  @Override
  public double reach() {
    double tmp = DreamMath.max(this.collisionBox().reach(),
        this.hitBox().reach(), SIZE);

    return tmp + speedCap();
  }

  @Override
  public void kill() {
    isAlive = false;
  }

  public void pickUpItem(ChunkMap chunkMap, Collection<Chunk> chunksInRange) {
    Collection<Item> items = chunkMap.itemsFromChunks(chunksInRange);
    Item closest = null;
    for (Item i : items) {
      if (closest == null) {
        closest = i;
      } else {
        closest = i.center().distance(center()) < closest.center()
            .distance(center()) ? i : closest;
      }
    }
    if (closest != null
        && closest.center().distance(center()) < ITEM_PICK_RANGE) {
      Logger.logDebug("Picked up an item");
      inventory.addItem(closest);
      chunkMap.removeItem(closest);

    }
  }

}
