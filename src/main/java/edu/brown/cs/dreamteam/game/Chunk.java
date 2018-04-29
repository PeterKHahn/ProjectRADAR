package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.item.Item;

/**
 * Chunked pieces of a Map for Efficiency purposes.
 *
 * @author peter
 *
 */
public class Chunk {

  private Set<DynamicEntity> dynamicEntities;
  private Set<StaticEntity> staticEntities;
  private Set<Item> items;

  private Set<CollisionBoxed> collisionBoxed;
  private Set<HitBoxed> hitBoxed;
  private Set<HurtBoxed> hurtBoxed;

  private int row;
  private int col;

  /**
   * A Constructor for Chunk initializing its row and column in the Chunk Grid.
   *
   * @param row
   *          the row that the chunk belongs to
   * @param col
   *          the column that the chunk belongs to
   */
  public Chunk(int row, int col) {
    this.row = row;
    this.col = col;
    init();

  }

  private void init() {
    dynamicEntities = new HashSet<DynamicEntity>();
    staticEntities = new HashSet<StaticEntity>();
    collisionBoxed = new HashSet<>();
    hitBoxed = new HashSet<>();
    hurtBoxed = new HashSet<>();

  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  /**
   * Adds a DynamicEntity to the Chunk.
   *
   * @param dynamimc
   *          The dynamic entity we are adding
   */
  public void addDynamic(DynamicEntity dynamic) {
    dynamicEntities.add(dynamic);
    collisionBoxed.add(dynamic);
  }

  /**
   * Removes a Player from the Chunk.
   *
   * @param player
   *          the player we are removing
   */
  public void removeDynamic(DynamicEntity dynamic) {
    dynamicEntities.remove(dynamic);
    collisionBoxed.remove(dynamic);
  }

  /**
   * Adds a Obstacle to the chunk
   *
   * @param o
   *          the obstacle
   */
  public void addStatic(StaticEntity staticEntity) {
    staticEntities.add(staticEntity);
  }

  /**
   * Returns a Collection of all static Entities in the Chunk.
   *
   * @return a collection of all static entities in the Chunk
   */
  public Collection<StaticEntity> getStaticEntities() {
    return staticEntities;
  }

  public void addCollisionBoxedEntities(CollisionBoxed entity) {
    collisionBoxed.add(entity);
  }

  public Collection<CollisionBoxed> getCollisionBoxedEntities() {
    return collisionBoxed;
  }

  public void addHitBoxed(HitBoxed entity) {
    hitBoxed.add(entity);
  }

  public Collection<HitBoxed> getHitboxed() {
    return hitBoxed;
  }

  public void addHurtBoxed(HurtBoxed entity) {
    hurtBoxed.add(entity);
  }

  public Collection<HurtBoxed> getHurtBoxed() {
    return hurtBoxed;
  }

  /**
   * Returns a Collection of all dynamic Entities in the Chunk
   *
   * @return a collection of all dynamic entities in the Chunk
   */
  public Collection<DynamicEntity> getDynamicEntities() {
    return dynamicEntities;
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void tick() {

  }

  @Override
  public String toString() {
    if (dynamicEntities.isEmpty()) {
      return "Empty Chunk";
    } else {
      return "Chunk";
    }
  }

}
