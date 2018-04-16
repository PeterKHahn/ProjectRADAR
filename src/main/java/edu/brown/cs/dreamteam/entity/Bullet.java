package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.CircleBox;
import edu.brown.cs.dreamteam.box.Edge;
import edu.brown.cs.dreamteam.box.Point;
import edu.brown.cs.dreamteam.box.RectangleBox;
import edu.brown.cs.dreamteam.game.ChunkMap;

public class Bullet extends Entity {

  private double xVelocity;
  private double yVelocity;

  private double x;
  private double y;

  private double lastX;
  private double lastY;

  private final double speed = 50;

  private static int count = 0;

  public static Bullet bullet(double x, double y, double theta) {
    String id = "BULLET:" + count;
    count++;
    return new Bullet(id, x, y, theta);
  }

  private Bullet(String id, double x, double y, double theta) {
    super(id);
    this.x = x;
    this.y = y;

    this.lastX = x;
    this.lastY = y;

    this.xVelocity = speed * Math.cos(theta);
    this.yVelocity = speed * Math.sin(theta);
  }

  public void update() {
    lastX = x;
    lastY = y;

    x += xVelocity;
    y += yVelocity;

  }

  public boolean collides(RectangleBox box) {
    if (box.isInBounds(lastX, lastY) || box.isInBounds(x, y)) {
      return true;
    }

    Point a = new Point(x, y);
    Point b = new Point(lastX, lastY);
    Edge tmp = new Edge(a, b);

    for (Edge e : box.getEdges()) {
      boolean res = tmp.collides(e);
      if (res) {
        return true;
      }
    }

    return false;

  }

  public boolean collides(CircleBox box) {
    return false;
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    // TODO Auto-generated method stub

  }

}
