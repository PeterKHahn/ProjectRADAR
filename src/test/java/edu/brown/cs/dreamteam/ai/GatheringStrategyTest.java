package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class GatheringStrategyTest {

  @Test
  public void testConstruction() {
    GatheringStrategy s = new GatheringStrategy();
    assertNotNull(s);
  }

}
