package edu.brown.cs.dreamteam.box;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EdgeTest {

  @Test
  public void testCollidesSquare() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 1);
    Point p3 = new Point(1, 0);
    Point p4 = new Point(1, 1);

    Edge e1 = new Edge(p1, p2);
    Edge e2 = new Edge(p2, p3);
    Edge e3 = new Edge(p3, p4);
    Edge e4 = new Edge(p4, p1);

    System.out.println(e1.collides(e1));
    System.out.println(e1.collides(e2));
    System.out.println(e1.collides(e3));
    System.out.println(e1.collides(e4));

    System.out.println(e2.collides(e1));
    System.out.println(e2.collides(e2));
    System.out.println(e2.collides(e3));
    System.out.println(e2.collides(e4));

    System.out.println(e3.collides(e1));
    System.out.println(e3.collides(e2));
    System.out.println(e3.collides(e3));
    System.out.println(e3.collides(e4));

    System.out.println(e4.collides(e1));
    System.out.println(e4.collides(e2));
    System.out.println(e4.collides(e3));
    System.out.println(e4.collides(e4));

  }

  @Test
  public void testCollides() {
    Point p00 = new Point(0, 0);
    Point p10 = new Point(1, 0);
    Point p20 = new Point(2, 0);
    Point p01 = new Point(0, 1);
    Point p11 = new Point(1, 1);
    Point p21 = new Point(2, 1);

    Edge p00p11 = new Edge(p00, p11);

    Edge p01p10 = new Edge(p01, p10);

    assertTrue(p00p11.collides(p01p10));

  }

}
