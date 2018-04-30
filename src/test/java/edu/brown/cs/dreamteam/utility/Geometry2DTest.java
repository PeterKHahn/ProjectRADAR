package edu.brown.cs.dreamteam.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs.dreamteam.box.Point;

public class Geometry2DTest {

  @Test
  public void testDistance() {
    assertEquals(Geometry2D.distance(0, 0, 0, 0), 0, 0.0001);
    assertEquals(Geometry2D.distance(0, 2, 0, 0), 2, 0.0001);
    assertEquals(Geometry2D.distance(2, 0, 0, 0), 2, 0.0001);
    assertEquals(Geometry2D.distance(0, 0, 2, 0), 2, 0.0001);
    assertEquals(Geometry2D.distance(0, 0, 0, 2), 2, 0.0001);
    assertEquals(Geometry2D.distance(0, 0, 3, 4), 5, 0.0001);
    assertEquals(Geometry2D.distance(0, 0, -4, -3), 5, 0.0001);
  }

  @Test
  public void testClockWise() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(5, 5);
    Point p3 = new Point(10, 0);

    assertFalse(Geometry2D.counterClockWise(p1, p2, p3));
    assertFalse(Geometry2D.counterClockWise(p2, p3, p1));
    assertFalse(Geometry2D.counterClockWise(p3, p1, p2));

    assertTrue(Geometry2D.counterClockWise(p1, p3, p2));
    assertTrue(Geometry2D.counterClockWise(p3, p2, p1));
    assertTrue(Geometry2D.counterClockWise(p2, p1, p3));

  }

}
