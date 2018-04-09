package edu.brown.cs.dreamteam.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
