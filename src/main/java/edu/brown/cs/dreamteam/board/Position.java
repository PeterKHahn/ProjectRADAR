package edu.brown.cs.dreamteam.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.brown.cs.dreamteam.graph.Vertex;

/**
 * Represents a position on the game map and as a vertex in the graph
 * representation of the game map.
 *
 * @author efu2
 */
public class Position implements Vertex<Position, Move> {
  private double x;
  private double y;
  private List<Move> edges;

  /**
   * Represents a position on the game map.
   * 
   * @param x
   *          The x coordinate of this position.
   * @param y
   *          The y coordinate of this position.
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
    edges = new ArrayList<>();
  }

  /**
   * Gets the x coordinate of this position.
   * 
   * @return An int representing the x coordinate of this position.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate of this position.
   * 
   * @return An int representing the y coordinate of this position.
   */
  public double getY() {
    return y;
  }

  public void addEdge(Position other) {
    edges.add(new Move(this, other));
  }

  @Override
  public List<Move> getEdges() {
    return edges;
  }

  @Override
  public double distanceTo(Position other) {
    return Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Position)) {
      return false;
    }

    Position other = (Position) o;
    if (new HashSet<>(other.getEdges()).equals(new HashSet<>(edges))
        && other.getX() == x && other.getY() == y) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, edges);
  }
}
