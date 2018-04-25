package edu.brown.cs.dreamteam.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.graph.AStarSearch;
import edu.brown.cs.dreamteam.graph.Path;

/**
 * Represents the game board as a graph with Positions as vertices and Moves as
 * edges.
 *
 * @author efu2
 */
public class Board {
  private final int width;
  private final int height;
  private AStarSearch<Position, Move> search;
  private Map<List<Double>, Position> positions;

  /**
   * Constructs the graph using the given entity information at the beginning of
   * the game.
   *
   * @param chunks
   *          A ChunkMap containing all entities in the game.
   */
  public Board(ChunkMap chunks) {
    width = chunks.getTotalWidth();
    height = chunks.getTotalHeight();
    search = new AStarSearch<>();
    constructGraph(chunks);
  }

  public Position addPosition(double x, double y) {
    Position pos = new Position(x, y);
    positions.put(ImmutableList.of(x, y), pos);
    return pos;
  }

  public void removePosition(double x, double y) {
    positions.remove(ImmutableList.of(x, y));
  }

  private void constructGraph(ChunkMap chunks) {
    positions = new HashMap<>();
    // Only make positions at the edges of obstacles and at every chunk border
    // along the edges of the map
    // TODO
  }

  /**
   * Adds a Move (edge) between this position and all other positions that are
   * not obstructed by an obstacle.
   *
   * @param pos
   *          The Position to add edges to.
   */
  private void addEdges(Position pos) {
    // TODO Find which other positions have an unobstructed straight line
    // distance from the given position, add Move and set weight to Euclidean
    // distance
  }

  /**
   * Does A* Search between the given start and end position and gives the next
   * position that the player should go to to reach the end in the shortest
   * path.
   *
   * @param start
   *          The starting position.
   * @param end
   *          The destination position.
   * @return A position adjacent to the starting position that starts the
   *         shortest path to the destination.
   */
  public Position getMoveTo(Position start, Position end) {
    Path<Position, Move> path = search.getShortestPath(start, end);
    Move first = path.getConnections().get(0);
    return first.getDest();
  }

  /**
   * Returns the Position on the edge of the map that is closest to the line
   * defined by pos and dir.
   * 
   * @param pos
   *          The position that the player is at.
   * @param dir
   *          The direction that the player wants to go in.
   * @return A Position on the edge of the Map that is closest to the line
   *         defined by pos and dir.
   */
  public Position getEdgePosition(Position pos, Vector dir) {
    // TODO
    return null;
  }

}
