package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.Boxed;
import edu.brown.cs.dreamteam.box.CircleBox;
import edu.brown.cs.dreamteam.game.Tickable;

/**
 * A dynamic entity is an entity that has a dynamic position and angle.
 * 
 * @author peter
 *
 */
public abstract class DynamicEntity extends Entity implements Tickable, Boxed {

  private double xVelocity;
  private double yVelocity;

  private double x;
  private double y;

  private double speed = 1;
  private double speedCap = speed * 2;

  private int vertCoeff;
  private int horzCoeff;

  private double theta;

  private double size;

  private CircleBox box;

  private String type;

  /**
   * Standard constructor for dynamic entity, initializing their fields
   * 
   * @param id
   *          id of the dynamicEntity
   * @param x
   *          the initial x value of the entity
   * @param y
   *          the initial y value of the entity
   * @param size
   *          the radius of the entity
   */
  public DynamicEntity(String id, double x, double y, double size) {
    super(id);
    this.size = size;
    this.x = x;
    this.y = y;
    init();
  }

  private void init() {
    box = new CircleBox(getXPos(), getYPos(), size);

  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public double getXPos() {
    return x;
  }

  public double getYPos() {
    return y;
  }

  public void setXPos(double x) {
    this.x = x;
    this.box.updateX(x);

  }

  public void setYPos(double y) {
    this.y = y;
    this.box.updateX(y);

  }

  /**
   * Updates the position given the dynamic entity's velocity
   */
  public void updatePosition() {
    updateX();
    updateY();

  }

  /**
   * Updates the x position of the entity given its x velocity
   */
  public void updateX() {
    setXPos(getXPos() + xVelocity);

  }

  /**
   * Updates the y position of the entity given its y velocity
   */
  public void updateY() {
    setYPos(getYPos() + yVelocity);

  }

  /**
   * Sets the x and y velocity given the entity's speed and angle
   * 
   * @param vertCoeff
   * @param horzCoeff
   * @param theta
   */
  protected void updateDynamic(int vertCoeff, int horzCoeff, double theta) {
    this.theta = theta;

    this.xVelocity = horzCoeff * speed;
    this.yVelocity = vertCoeff * speed;
  }

  /**
   * Returns the x velocity of the entity
   * 
   * @return the x velocity of the entity
   */
  public double getXVelocity() {
    return xVelocity;
  }

  /**
   * Returns the y velocity of the entity
   * 
   * @return the y velocity of the entity
   */
  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public double getUpper() {
    return box.getUpper() + speedCap;
  }

  @Override
  public double getLower() {
    return box.getLower() - speedCap;
  }

  @Override
  public double getLeft() {
    return box.getLeft() - speedCap;
  }

  @Override
  public double getRight() {
    return box.getRight() + speedCap;
  }

  /**
   * Given an entity that should no longer exist, this method sets its internal
   * state to no longer be active
   */
  public abstract void kill();

  /**
   * A method to determine whether this entity has collided with another
   * StaticEntity
   * 
   * @param e
   *          the static entity to test for collision/
   * @return true if we collide, false otherwise. Collision is defined by if
   *         there exists a point of overlap between our two boxes
   */
  public boolean collidesWith(StaticEntity e) {

    return this.box.collides(e.getBox());
  }

  /**
   * Returns the radius of this entity
   * 
   * @return the radius of this entity
   */
  public double getRadius() {
    return this.box.getRadius();
  }

}
