package edu.brown.cs.dreamteam.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClampTest {

  @Test
  public void testConstructor() {
    assertNotNull(new Clamp(1, 2));
    assertNotNull(new Clamp(2, 1));
    assertNotNull(new Clamp(1.0, 2.0));
    assertNotNull(new Clamp(-5.0, 10.0));
  }

  @Test
  public void testClamp() {
    Clamp alpha = new Clamp(-5.0, 10.0);
    assertEquals(alpha.clamp(0), 0, 0.0001);
    assertEquals(alpha.clamp(-1.0), -1.0, 0.0001);
    assertEquals(alpha.clamp(1.0), 1.0, 0.0001);
    assertEquals(alpha.clamp(-5.0), -5.0, 0.0001);
    assertEquals(alpha.clamp(10.0), 10.0, 0.0001);
    assertEquals(alpha.clamp(11.0), 10.0, 0.0001);
    assertEquals(alpha.clamp(-6.0), -5.0, 0.0001);

    /*
     * Testing reversability
     */
    Clamp beta = new Clamp(10.0, -5.0);
    assertEquals(beta.clamp(0), 0, 0.0001);
    assertEquals(beta.clamp(-1.0), -1.0, 0.0001);
    assertEquals(beta.clamp(1.0), 1.0, 0.0001);
    assertEquals(beta.clamp(-5.0), -5.0, 0.0001);
    assertEquals(beta.clamp(10.0), 10.0, 0.0001);
    assertEquals(beta.clamp(11.0), 10.0, 0.0001);
    assertEquals(beta.clamp(-6.0), -5.0, 0.0001);
  }

  @Test
  public void testOverlaps() {
    Clamp alpha = new Clamp(5, 10);
    Clamp bravo = new Clamp(11, 13);
    Clamp charlie = new Clamp(8, 12);
    Clamp delta = new Clamp(3, 15);
    Clamp echo = new Clamp(1, 3);
    Clamp foxtrot = new Clamp(15, 17);

    assertTrue(alpha.overlaps(alpha));
    assertFalse(alpha.overlaps(bravo));
    assertTrue(alpha.overlaps(charlie));
    assertTrue(alpha.overlaps(delta));
    assertFalse(alpha.overlaps(echo));
    assertFalse(alpha.overlaps(foxtrot));

    assertFalse(bravo.overlaps(alpha));
    assertTrue(bravo.overlaps(bravo));
    assertTrue(bravo.overlaps(charlie));
    assertTrue(bravo.overlaps(delta));
    assertFalse(bravo.overlaps(echo));
    assertFalse(bravo.overlaps(foxtrot));

    assertTrue(alpha.overlaps(charlie));
    assertTrue(charlie.overlaps(bravo));
    assertTrue(charlie.overlaps(charlie));
    assertTrue(charlie.overlaps(delta));
    assertFalse(charlie.overlaps(echo));
    assertFalse(charlie.overlaps(foxtrot));

    assertTrue(delta.overlaps(alpha));
    assertTrue(delta.overlaps(bravo));
    assertTrue(delta.overlaps(charlie));
    assertTrue(delta.overlaps(delta));
    assertTrue(delta.overlaps(echo));
    assertTrue(delta.overlaps(foxtrot));

    assertFalse(echo.overlaps(alpha));
    assertFalse(echo.overlaps(bravo));
    assertFalse(echo.overlaps(charlie));
    assertTrue(echo.overlaps(delta));
    assertTrue(echo.overlaps(echo));
    assertFalse(echo.overlaps(foxtrot));

    assertFalse(foxtrot.overlaps(alpha));
    assertFalse(foxtrot.overlaps(bravo));
    assertFalse(foxtrot.overlaps(charlie));
    assertTrue(foxtrot.overlaps(delta));
    assertFalse(foxtrot.overlaps(echo));
    assertTrue(foxtrot.overlaps(foxtrot));
  }

}
