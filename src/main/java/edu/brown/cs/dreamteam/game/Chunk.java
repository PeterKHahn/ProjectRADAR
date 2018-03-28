package edu.brown.cs.dreamteam.game;

import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.dreamteam.entity.DynamicEntity;

public class Chunk implements Tickable {

  private Set<DynamicEntity> dynamicEntities;

  private int row;
  private int col;

  public Chunk(int row, int col) {
    this.row = row;
    this.col = col;
    init();

  }

  private void init() {
    dynamicEntities = new HashSet<DynamicEntity>();
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public void addDynamic(DynamicEntity d) {
    dynamicEntities.add(d);
  }

  public void removeDynamic(DynamicEntity d) {
    dynamicEntities.remove(d);
  }

  @Override
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
