package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.Tickable;

public abstract class DynamicEntity extends Entity implements Tickable {

  private double xVelocity;
  private double yVelocity;

  private double movementSpeed = 1;
  private double strafeSpeed = 1;

  private int movementCoeff;
  private int strafeCoeff;

  private double theta;

  public DynamicEntity(String id, double x, double y) {
    super(id, x, y);
  }

  protected void updatePosition() {
    setXPos(getXPos() + xVelocity);
    setYPos(getYPos() + yVelocity);
  }

  protected void updateDynamic(ClientState state) {
    movementCoeff = state.retrieveMovementMultiplier();
    strafeCoeff = state.retrieveStrafeMultiplier();
    theta = state.retrieveTheta();
    updateVelocity();
  }

  private void updateVelocity() {

    double tempL = strafeCoeff * strafeSpeed;
    double tempF = movementCoeff * movementSpeed;

    xVelocity = tempL * Math.cos(theta);
    yVelocity = tempF * Math.sin(theta);

  }

  public void setXVelocity(int xVelocity) {
    this.xVelocity = xVelocity;
  }

  public void setYVelocity(int yVelocity) {
    this.yVelocity = yVelocity;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }

}
