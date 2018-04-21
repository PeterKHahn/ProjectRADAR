package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.Point;
import edu.brown.cs.dreamteam.game.ChunkMap;

/**
 * A StaticEntity that represents Solid, Rectangular Obstacles in the field.
 * 
 * @author peter
 *
 */
public class Obstacle extends StaticEntity {

  private BoxSet box;

  /**
   * Creates an Obstacle.
   * 
   * @param id
   *          the id of the Obstacle
   * @param center
   *          The Center point the y position of the obstacle
   * @param radius
   *          the radius of the obstacle
   */
  public Obstacle(String id, Point center, double radius) {
    super(id, center, radius);
    box = new BoxSet(new Box(center, radius));

  }

  @Override
  public void tick(ChunkMap chunkMap) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isSolid() {
    return true;
  }

  @Override
  public BoxSet collisionBox() {
    return box;
  }

  @Override
  public double reach() {
    return box.reach();
  }

}
