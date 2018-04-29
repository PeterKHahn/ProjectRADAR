package edu.brown.cs.dreamteam.entity;

import java.util.Collection;
import java.util.Map.Entry;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.CollisionBoxed;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.utility.Clamp;
import edu.brown.cs.dreamteam.utility.DreamMath;
import edu.brown.cs.dreamteam.utility.Logger;

/**
 * A dynamic entity is an entity that has a dynamic position and angle.
 * 
 * @author peter
 *
 */
public abstract class DynamicEntity extends Entity implements CollisionBoxed {

  private Vector velocityVector;

  private double speed = 1;
  private double radius;
  private Vector center;

  private BoxSet collisionBox;

  private Clamp timeClamp;

  /**
   * Standard constructor for dynamicentity, initializing their fields.
   * 
   * @param id
   *          id of the dynamicEntity
   * @param x
   *          the initial x value of the entity
   * @param y
   *          the initial y value of the entity
   * @param radius
   *          the radius of the entity
   */
  public DynamicEntity(String id, double x, double y, double radius) {
    super(id);
    this.radius = radius;
    this.velocityVector = new Vector(0, 0);
    this.center = new Vector(x, y);
    this.collisionBox = new BoxSet(new Box(radius));
    init();
  }

  private void init() {
    timeClamp = new Clamp(0, 1);
  }

  public double speedCap() {
    return 2 * speed;
  }

  /**
   * Updates the position given the dynamic entity's velocity.
   */
  public void updatePosition(ChunkMap chunks) {
    Logger.logDebug(center.toString());
    Collection<Chunk> chunksNear = chunks.chunksInRange(this);

    for (Chunk chunk : chunksNear) {
      chunk.removeDynamic(this);
    }

    Collection<CollisionBoxed> collidables = chunks
        .getCollisionedFromChunks(chunksNear);
    Logger.logDebug("Number of collidables: " + collidables.size());
    double minT = 1;
    for (CollisionBoxed c : collidables) {
      if (!c.isSolid()) {
        continue;
      }
      double res = handleDynamicCollision(c);
      minT = Math.min(res, minT);
    }

    changePosition(velocityVector.scalarMultiply(minT));

    Collection<Chunk> newChunks = chunks.chunksInRange(this);

    chunks.addDynamic(this, newChunks);
  }

  public void changePosition(Vector v) {
    center = center.add(v);
  }

  /**
   * Returns the time at which the dynamic Entity will first collide with the
   * static entity.
   * 
   * @param staticBoxSet
   *          The Set of Static BoxSets that we are colliding against
   * @return
   */
  private double handleDynamicCollision(CollisionBoxed collisionBoxed) {
    BoxSet staticBoxSet = collisionBoxed.collisionBox();
    double minT = 1;

    for (Entry<Box, Vector> dynamicBoxEntry : collisionBox.boxes().entrySet()) {
      for (Entry<Box, Vector> staticBoxEntry : staticBoxSet.boxes()
          .entrySet()) {
        Logger.logDebug("Testing for collision...");

        Box dynamicBox = dynamicBoxEntry.getKey();
        Box staticBox = staticBoxEntry.getKey();

        Vector dynamicCenter = center.add(collisionBoxOffset())
            .add(dynamicBoxEntry.getValue());

        Vector staticCenter = collisionBoxed.center().add(collisionBoxOffset())
            .add(staticBoxEntry.getValue());

        Logger.logDebug("Dynamic Center: " + dynamicCenter);
        Logger.logDebug("Static Center: " + staticCenter);

        Logger.logDebug(
            "Distance between: " + dynamicCenter.distance(staticCenter));

        Vector u1 = dynamicCenter;
        Vector u2 = staticCenter;

        Vector u3 = u2.subtract(u1);
        double time = u3.projectOntoMagnitude(this.velocityVector);

        double timeOfMinimumDistance = timeClamp.clamp(time);
        Vector vPrime = velocityVector.scalarMultiply(timeOfMinimumDistance)
            .add(u1);
        double distanceSquared = vPrime.subtract(u2).magnitudeSquared();
        boolean collides = distanceSquared <= (dynamicBox.radius()
            + staticBox.radius()) * (dynamicBox.radius() + staticBox.radius());
        if (collides) {
          // calculate the maximum time before collision
          double a = velocityVector.innerProduct(velocityVector);
          double b = 2 * u3.innerProduct(velocityVector);
          double c = u3.magnitudeSquared();

          double tPrime = DreamMath.quadratic(a, b, c, true);
          minT = Math.min(tPrime, minT);

        }
      }
    }

    return minT;

  }

  /**
   * Given a clientstate, updates the internal fields of the dynamic entity to
   * match those specified in the ClientState.
   * 
   * 
   */
  protected void updateDynamic(int vertCoeff, int horzCoeff) {
    velocityVector = new Vector(horzCoeff * speed, vertCoeff * speed);

  }

  /**
   * Returns the x velocity of the entity.
   * 
   * @return the x velocity of the entity
   */
  public double getXVelocity() {
    return velocityVector.x;
  }

  /**
   * Returns the y velocity of the entity.
   * 
   * @return the y velocity of the entity
   */
  public double getYVelocity() {
    return velocityVector.y;
  }

  /**
   * Given an entity that should no longer exist, this method sets its internal
   * state to no longer be active.
   */
  public abstract void kill();

  /**
   * Returns the radius of this entity.
   * 
   * @return the radius of this entity
   */
  public double getRadius() {
    return radius;
  }

  @Override
  public boolean isSolid() {
    return false;
  }

  @Override
  public BoxSet collisionBox() {
    return collisionBox;
  }

  @Override
  public Vector center() {
    return center;
  }

}
