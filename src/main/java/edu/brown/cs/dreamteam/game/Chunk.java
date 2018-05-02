package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.Interactable;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.item.Item;

/**
 * Chunked pieces of a Map for Efficiency purposes.
 *
 * @author peter
 *
 */
public class Chunk {

  private Set<Interactable> interactable;
  private Set<StaticEntity> statics;
  private Set<DynamicEntity> dynamics;

  private Set<Item> items;

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
    statics = new HashSet<StaticEntity>();
    dynamics = new HashSet<DynamicEntity>();
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

  public void removeInteractable(Interactable e) {
    interactable.remove(e);
  }

  public void addDynamic(DynamicEntity e) {
    dynamics.add(e);
    addInteractable(e);
  }

  public Set<DynamicEntity> getDynamic() {
    return dynamics;
  }

  public void removeDynamic(DynamicEntity e) {
    dynamics.remove(e);
    removeInteractable(e);
  }

  public void addStatic(StaticEntity e) {
    statics.add(e);
  }

  public Set<StaticEntity> getStatic() {
    return statics;
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
