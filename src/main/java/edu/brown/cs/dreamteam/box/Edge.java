package edu.brown.cs.dreamteam.box;

import edu.brown.cs.dreamteam.utility.Geometry2D;

public class Edge {

  public final Point p1;
  public final Point p2;

  public Edge(Point p1, Point p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  public boolean collides(Edge edge) {
    Point a = this.p1;
    Point b = this.p2;
    Point c = edge.p1;
    Point d = edge.p2;
    boolean res1 = Geometry2D.counterClockWise(a, c, d) != Geometry2D
        .counterClockWise(b, c, d);
    boolean res2 = Geometry2D.counterClockWise(a, b, c) != Geometry2D
        .counterClockWise(a, b, d);

    return res1 && res2;
  }

}
