package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.CircleBox;
import edu.brown.cs.dreamteam.box.RectangleBox;
import edu.brown.cs.dreamteam.datastructures.Edge;
import edu.brown.cs.dreamteam.datastructures.Point;
import edu.brown.cs.dreamteam.utility.Geometry2D;

public class Bullet {

  private double xVelocity;
  private double yVelocity;

  private double x;
  private double y;

  private double lastX;
  private double lastY;

  private final double speed = 50;

  public Bullet(double x, double y, double theta) {
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

    for (Edge e : box.getEdges()) {
      boolean res = collides(a, b, e);
      if (res) {
        return true;
      }
    }

    return false;

  }

  private boolean collides(Point a, Point b, Edge e) {
    Point c = e.p1;
    Point d = e.p2;
    Geometry2D.counterClockWise(a, c, d);
    return false;
  }

  public boolean collides(CircleBox box) {
    return false;
  }

}
