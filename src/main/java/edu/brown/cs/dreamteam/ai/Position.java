package edu.brown.cs.dreamteam.ai;

import edu.brown.cs.dreamteam.graph.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a position on the game map and as a vertex in the graph
 * representation of the game map.
 *
 * @author efu2
 */
public class Position implements Vertex<Position, Move> {
  private int x;
  private int y;
  private List<Move> edges;

  /**
   * Represents a position on the game map.
   * 
   * @param x
   *          The x coordinate of this position.
   * @param y
   *          The y coordinate of this position.
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
    edges = new ArrayList<>();
  }

  /**
   * Gets the x coordinate of this position.
   * 
   * @return An int representing the x coordinate of this position.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y coordinate of this position.
   * 
   * @return An int representing the y coordinate of this position.
   */
  public int getY() {
    return y;
  }

  @Override
  public List<Move> getEdges() {
    return edges;
  }

  @Override
  public double distanceTo(Position other) {
    return Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2);
  }

}
