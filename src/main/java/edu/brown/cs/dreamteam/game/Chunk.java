package edu.brown.cs.dreamteam.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.DynamicEntity;
import edu.brown.cs.dreamteam.entity.StaticEntity;

/**
 * Chunked pieces of a Map for Efficiency purposes
 * 
 * @author peter
 *
 */
public class Chunk {

  private Set<DynamicEntity> dynamicEntities;
  private Set<StaticEntity> staticEntities;

  private int row;
  private int col;

  /**
   * A Constructor for Chunk initializing its row and column in the Chunk Grid
   * 
   * @param row
   * @param col
   */
  public Chunk(int row, int col) {
    this.row = row;
    this.col = col;
    init();

  }

  private void init() {
    dynamicEntities = new HashSet<DynamicEntity>();
    staticEntities = new HashSet<StaticEntity>();

  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  /**
   * Adds a DynamicEntity to the Chunk
   * 
   * @param d
   */
  public void addDynamic(DynamicEntity d) {
    dynamicEntities.add(d);
  }

  /**
   * Removes a DynamicEntity from the Chunk
   * 
   * @param d
   */
  public void removeDynamic(DynamicEntity d) {
    dynamicEntities.remove(d);
  }

  /**
   * Adds a Static Entity to the Chunk
   * 
   * @param s
   */
  public void addStatic(StaticEntity s) {
    staticEntities.add(s);
  }

  /**
   * Returns a Collection of all static Entities in the Chunk
   * 
   * @return a collection of all static entities in the Chunk
   */
  public Collection<StaticEntity> getStaticEntities() {
    return staticEntities;
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
