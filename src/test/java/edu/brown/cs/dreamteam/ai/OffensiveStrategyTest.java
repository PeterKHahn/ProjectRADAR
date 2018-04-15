package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class OffensiveStrategyTest {

  @Test
  public void testConstruction() {
    OffensiveStrategy s = new OffensiveStrategy();
    assertNotNull(s);
  }

}
