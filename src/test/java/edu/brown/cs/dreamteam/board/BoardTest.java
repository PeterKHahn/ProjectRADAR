package edu.brown.cs.dreamteam.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Obstacle;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.game.GameEngine;
import edu.brown.cs.dreamteam.main.SystemArchitect;

public class BoardTest {
  private static ChunkMap emptyChunks;
  private static GameEngine oneObstacle;
  private static GameEngine multObstacles;

  @Before
  public void setup() {
    Board.setEntitySize(1);

    emptyChunks = new ChunkMap(10, 10, 1);

    SystemArchitect architect = new SystemArchitect();

    oneObstacle = new GameEngine(architect, 10, 10, 1);
    oneObstacle.addStatic(new Obstacle("1", new Vector(5, 5), 0));
    oneObstacle.board();

    multObstacles = new GameEngine(architect, 10, 10, 1);
    multObstacles.addStatic(new Obstacle("1", new Vector(2, 2), 0));
    multObstacles.addStatic(new Obstacle("2", new Vector(7, 7), 1));

    multObstacles.board();
  }

  @Test
  public void testConstruction() {
    Board b = new Board(emptyChunks);
    assertNotNull(b);
  }

  @Test
  public void testPositionsInEmptyBoard() {
    Board b = new Board(emptyChunks);
    List<Position> positions = b.getPositions();
    assertEquals(40, positions.size());
  }

  @Test
  public void testEdgesInEmptyBoard() {
    Board b = new Board(emptyChunks);
    List<Position> positions = b.getPositions();
    for (Position pos : positions) {
      List<Move> edges = pos.getEdges();
      for (Position otherPos : positions) {
        if (pos.equals(otherPos)) {
          // Positions should not have a loopback edge
          assertFalse(edges.contains(new Move(pos, otherPos)));
        } else {
          // All map edge positions should have edges to one another in an
          // empty map
          assertTrue(edges.contains(new Move(pos, otherPos)));
        }
      }
    }
  }

  @Test
  public void testAddPositionToEmptyBoard() {
    Board b = new Board(emptyChunks);
    Position test = new Position(5, 5);
    b.addEdgesFor(test, false);
    List<Move> edges = test.getEdges();
    List<Position> boardPositions = b.getPositions();
    assertEquals(40, edges.size());
    for (Position position : boardPositions) {
      Move move = new Move(test, position);
      assertTrue(edges.contains(move));
    }
  }

  @Test
  public void testPositionsWithOneObstacle() {
    Board b = oneObstacle.getBoard();
    List<Position> positions = b.getPositions();
    assertEquals(44, positions.size());
  }

  @Test
  public void testEdgesWithOneObstacle() {
    Board b = oneObstacle.getBoard();
    Position curr;
    for (int i = 0; i < 10; i++) {
      // Position on top edge
      curr = new Position(i, 10);
      b.addEdgesFor(curr, false);
      List<Move> edges = curr.getEdges();
      if (i == 0 || i == 4 || i == 6) {
        assertEquals(35, edges.size());
      } else {
        assertEquals(36, edges.size());
      }

      // Position on right edge
      curr = new Position(10, 10 - i);
      b.addEdgesFor(curr, false);
      edges = curr.getEdges();
      if (i == 0 || i == 4 || i == 6) {
        assertEquals(35, edges.size());
      } else {
        assertEquals(36, edges.size());
      }

      // Position on bottom edge
      curr = new Position(10 - i, 0);
      b.addEdgesFor(curr, false);
      edges = curr.getEdges();
      if (i == 0 || i == 4 || i == 6) {
        assertEquals(35, edges.size());
      } else {
        assertEquals(36, edges.size());
      }

      // Position on left edge
      curr = new Position(0, i);
      b.addEdgesFor(curr, false);
      edges = curr.getEdges();
      if (i == 0 || i == 4 || i == 6) {
        assertEquals(35, edges.size());
      } else {
        assertEquals(36, edges.size());
      }
    }
  }

  @Test
  public void testAddPositionWithOneObstacle() {
    Board b = oneObstacle.getBoard();
    Position test = new Position(2, 2);
    b.addEdgesFor(test, false);
    List<Move> edges = test.getEdges();
    assertEquals(36, edges.size());
  }

  @Test
  public void testGetMoveTo() {
    Board b = oneObstacle.getBoard();
    Position test = new Position(2, 2);
    b.addEdgesFor(test, false);
    Position next = b.getMoveTo(test, new Position(10, 10));
    assertEquals(new Position(4, 6), next);
  }

}
