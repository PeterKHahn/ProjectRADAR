package edu.brown.cs.dreamteam.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.dreamteam.board.Board;
import edu.brown.cs.dreamteam.game.ChunkMap;

public class AiControllerTest {
  private static ChunkMap emptyChunks;
  private static Board emptyBoard;

  @Before
  public void setup() {
    emptyChunks = new ChunkMap(10, 10, 1);
    emptyBoard = new Board(emptyChunks);
  }

  @Test
  public void testConstruction() {
    AiController s = new AiController("1", emptyBoard);
    assertNotNull(s);
  }

}
