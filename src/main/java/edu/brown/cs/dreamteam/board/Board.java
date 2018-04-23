package edu.brown.cs.dreamteam.board;

import com.google.common.collect.ImmutableList;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.graph.AStarSearch;
import edu.brown.cs.dreamteam.graph.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private Map<List<Integer>, Position> positions;

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

  private void constructGraph(ChunkMap chunks) {
    positions = new HashMap<>();
    // Only make positions that the AI player can move to (taking into
    // account its size and hitbox)
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        // TODO check traversability by AI player first (obstacle collision
        // boxes and AI player's collision box
        Position curr = new Position(i, j);
        addEdges(curr);
        if (!positions.containsKey(ImmutableList.of(i, j))) {
          positions.put(ImmutableList.of(i, j), curr);
        }
      }
    }
  }

  /**
   * Adds a Move (edge) from this position to all adjacent traversable
   * positions.
   *
   * @param pos
   *          The Position to add edges to.
   */
  private void addEdges(Position pos) {
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        // TODO Check if neighbor position is inside an obstacle
        if (i != j) {
          Position neighbor;
          if (positions.containsKey(ImmutableList.of(i, j))) {
            neighbor = positions.get(ImmutableList.of(i, j));
          } else {
            neighbor = new Position(i, j);
          }
          pos.addEdge(neighbor);
        }
      }
    }
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
   * Checks if the position is valid (within the boundaries of the board, where
   * the coordinates are 0-indexed).
   *
   * @param position
   *          A Position object.
   * @return True if the position is in bounds, false otherwise.
   */
  public boolean isValid(Position position) {
    if (position.getX() >= 0 && position.getX() < width && position.getY() >= 0
        && position.getY() < height) {
      return true;
    }

    return false;
  }

  /**
   * Checks whether the given position is traversable by a player.
   *
   * @param position
   * 
   * @return
   */
  public boolean isTraversable(Position position) {
    if (isValid(position)) {
      if (position.getEdges().size() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the position object at the given coordinates.
   * 
   * @param x
   *          The x position.
   * @param y
   *          The y position.
   * @return
   */
  public Position getPosition(int x, int y) {
    List<Integer> coord = ImmutableList.of(x, y);
    if (positions.containsKey(coord)) {
      return positions.get(ImmutableList.of(x, y));
    }
    return null;
  }

}
