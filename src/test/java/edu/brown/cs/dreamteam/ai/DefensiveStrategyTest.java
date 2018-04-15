package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DefensiveStrategyTest {

  @Test
  public void testConstruction() {
    DefensiveStrategy s = new DefensiveStrategy();
    assertNotNull(s);
  }

}
