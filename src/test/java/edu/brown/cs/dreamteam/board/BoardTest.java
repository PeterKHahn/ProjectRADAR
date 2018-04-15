package edu.brown.cs.dreamteam.board;

import static org.junit.Assert.assertNotNull;

import edu.brown.cs.dreamteam.game.ChunkMap;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
  private static ChunkMap emptyChunks;

  @Before
  public void setup() {
    emptyChunks = new ChunkMap(10, 10, 1);
  }

  @Test
  public void testConstruction() {
    Board b = new Board(emptyChunks);
    assertNotNull(b);
  }

}
