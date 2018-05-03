package edu.brown.cs.dreamteam.entity;

import java.util.Collection;

import edu.brown.cs.dreamteam.box.Box;
import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.utility.Clamp;
import edu.brown.cs.dreamteam.utility.DreamMath;

/**
 * A dynamic entity is an entity that has a dynamic position and angle.
 * 
 * @author peter
 *
 */

public abstract class DynamicEntity extends Interactable {

  private Vector velocityVector;
  protected boolean isAlive;

  private double speed = 1;
  private double radius;
  private Vector center;

  private BoxSet collisionBox;

  private Clamp timeClamp;

  /**
   * Standard constructor for dynamic entity, initializing their fields.
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
    this.collisionBox = new BoxSet(radius);
    init();
  }

  private void init() {
    timeClamp = new Clamp(0, 1);
    isAlive = true;
  }

  public double speedCap() {
    return 2 * speed;
  }

  /**
   * Updates the position given the dynamic entity's velocity.
   */
  public void updatePosition(ChunkMap chunks) {
    Collection<Chunk> chunksNear = chunks.chunksInRange(this);

    Collection<Interactable> collidables = chunks
        .interactableFromChunks(chunksNear);
    double minT = 1;
    for (Interactable c : collidables) {
      if (!c.isSolid()) {
        continue;
      }
      double res = handleDynamicCollision(c);
      minT = Math.min(res, minT);
    }
    System.out.println(velocityVector.scalarMultiply(minT));
    changePosition(velocityVector.scalarMultiply(minT));

    // chunks.addDynamic(this, newChunks);
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
  private double handleDynamicCollision(Interactable collisionBoxed) {
    BoxSet staticBoxSet = collisionBoxed.collisionBox();
    double minT = 1;
    for (Box dynamicBox : collisionBox().boxes()) {
      for (Box staticBox : staticBoxSet.boxes()) {

        Vector dynamicCenter = dynamicBox.offset().add(center);
        Vector staticCenter = staticBox.offset().add(collisionBoxed.center());
        Vector u1 = dynamicCenter;
        Vector u2 = staticCenter;

        Vector u3 = u2.subtract(u1); // Difference vector
        double time = u3.projectOntoMagnitude(this.velocityVector);

        double timeOfMinimumDistance = timeClamp.clamp(time);
        Vector vPrime = velocityVector.scalarMultiply(timeOfMinimumDistance)
            .add(u1);
        double distanceSquared = vPrime.subtract(u2).magnitudeSquared();
        boolean collides = distanceSquared <= (dynamicBox.radius()
            + staticBox.radius()) * (dynamicBox.radius() + staticBox.radius());
        if (collides) {
          // calculate the maximum time before collision
          double sumRadiusSquared = (dynamicBox.radius() + staticBox.radius())
              * (dynamicBox.radius() + staticBox.radius());

          double a = velocityVector.magnitudeSquared();
          double b = -2 * u3.innerProduct(velocityVector);
          double c = u3.magnitudeSquared() - sumRadiusSquared;

          double tPrime = DreamMath.quadratic(a, b, c, true);
          if (tPrime > 0) {
            tPrime -= 0.0001; // Janky fix for now
          }
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
   * @param vertCoeff
   * @param horzCoeff
   */
  public void updateDynamic(int vertCoeff, int horzCoeff) {
    velocityVector = new Vector(horzCoeff * speed, vertCoeff * speed);
    System.out.println(velocityVector);
  }

  /**
   * Allows AiPlayers to move in a given direction.
   *
   * @param dir
   *          The direction to move in.
   */
  public void updateDynamic(Vector dir) {
    // Normalize vector and make its magnitude speed
    double magnitude = dir.magnitude();
    velocityVector = dir.scalarMultiply(speed / magnitude);
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
  public void kill() {
    isAlive = false;
  }

  public boolean alive() {
    return isAlive;
  }

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
