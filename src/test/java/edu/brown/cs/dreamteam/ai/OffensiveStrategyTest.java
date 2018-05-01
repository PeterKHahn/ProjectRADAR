package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.ChunkMap;
import org.junit.Before;
import org.junit.Test;

public class OffensiveStrategyTest {
  private static Board emptyBoard;
  private static AiPlayer player;

  @Before
  public void setup() {
    emptyBoard = new Board(new ChunkMap(10, 10, 1));
    player = new AiPlayer("1", 0, 0, 5);
  }

  @Test
  public void testConstruction() {
    OffensiveStrategy s = new OffensiveStrategy(emptyBoard, player);
    assertNotNull(s);
  }

}
