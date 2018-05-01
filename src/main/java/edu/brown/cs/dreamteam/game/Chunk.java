package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.box.HurtBoxed;
import edu.brown.cs.dreamteam.entity.Entity;
import edu.brown.cs.dreamteam.entity.Interactable;
import edu.brown.cs.dreamteam.item.Item;

/**
 * Chunked pieces of a Map for Efficiency purposes.
 *
 * @author peter
 *
 */
public class Chunk {

  private Set<Interactable> interactable;

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
    interactable = new HashSet<Interactable>();

    collisionBoxed = new HashSet<>();
    hitBoxed = new HashSet<>();
    hurtBoxed = new HashSet<>();
    items = new HashSet<Item>();

  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public void addInteractable(Interactable e) {
    interactable.add(e);
  }

  public Set<Interactable> getInteractable() {
    return interactable;
  }

  public void removeEntity(Entity e) {
    interactable.remove(e);
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void tick() {

  }

}
