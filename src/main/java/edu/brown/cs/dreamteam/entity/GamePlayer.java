package edu.brown.cs.dreamteam.entity;

public class GamePlayer extends Entity {

  private int xVelocity = 0;
  private int yVelocity = 0;

  public GamePlayer(String id, int row, int col) {
    super(id, row, col);

  }

  public void setXVelocity(int xVelocity) {
    this.xVelocity = xVelocity;
  }

  public void setYVelocity(int yVelocity) {
    this.yVelocity = yVelocity;
  }

  public int getXVelocity() {
    return xVelocity;
  }

  public int getYVelocity() {
    return yVelocity;
  }

  @Override
  public void tick() {
    System.out.println("I AM A PLAYER");

  }

}
