package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.RectangleBox;
import edu.brown.cs.dreamteam.datastructures.TwoDVector;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * A StaticEntity that represents Solid, Rectangular Obstacles in the field
 * 
 * @author peter
 *
 */
public class Obstacle extends StaticEntity {

  private TwoDVector upperLeft;
  private TwoDVector lowerRight;

  private RectangleBox box;

  /**
   * The constructor for Obstacle. UpperLeft and LowerRight are named as such,
   * but flipping these will still yield the intended result
   * 
   * @param id
   *          The ID of the obstacle
   * @param upperLeft
   *          the upperLeft corner of the Obstacle
   * @param lowerRight
   *          the lowerRight corner of the Obstacle
   */
  public Obstacle(String id, TwoDVector upperLeft, TwoDVector lowerRight) {
    super(id);
    this.upperLeft = upperLeft;
    this.lowerRight = lowerRight;
    init();
  }

  private void init() {
    box = new RectangleBox(upperLeft, lowerRight);
  }

  @Override
  public RectangleBox getBox() {
    return box;
  }

  @Override
  public double getUpper() {
    return box.getUpper();
  }

  @Override
  public double getLower() {
    return box.getLower();
  }

  @Override
  public double getLeft() {
    return box.getLeft();
  }

  @Override
  public double getRight() {
    return box.getRight();
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    // TODO Auto-generated method stub

  }

}
