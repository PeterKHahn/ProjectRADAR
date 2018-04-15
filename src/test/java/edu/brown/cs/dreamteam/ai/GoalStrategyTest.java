package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class GoalStrategyTest {

  @Test
  public void testConstruction() {
    GoalStrategy s = new GoalStrategy();
    assertNotNull(s);
  }

}
