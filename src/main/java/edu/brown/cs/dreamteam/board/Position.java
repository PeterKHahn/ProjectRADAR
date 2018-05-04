package edu.brown.cs.dreamteam.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.graph.Vertex;
import edu.brown.cs.dreamteam.kdtree.DimensionsComparable;
import edu.brown.cs.dreamteam.kdtree.DimensionsQueryable;
import edu.brown.cs.dreamteam.kdtree.DistanceCalculatable;

/**
 * Represents a position on the game map and as a vertex in the graph
 * representation of the game map.
 *
 * @author efu2
 */
public class Position extends Vector
    implements Vertex<Position, Move>, DimensionsComparable<Position>,
    DimensionsQueryable<Double>, DistanceCalculatable<Position> {
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
    super(x, y);
    edges = new ArrayList<>();
  }

  /**
   * Adds an edge and sets the edge weight to the Euclidean distance between the
   * given Position and this Position.
   * 
   * @param other
   *          The Position to add an edge to. Will do nothing if other is null.
   */
  public void addEdge(Position other) {
    if (other != null) {
      Move edge = new Move(this, other);
      edge.setWeight(other.distanceTo(this));
      edges.add(edge);
    }
  }

  /**
   * Removes the edge to the other position if it exists.
   * 
   * @param other
   *          The Position to remove the edge to.
   */
  public void removeEdge(Position other) {
    edges.remove(new Move(this, other));
  }

  @Override
  public List<Move> getEdges() {
    return edges;
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
    if (Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public Double distanceTo(Position other) {
    return Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2);
  }

  @Override
  public Double getDimension(int dimension) {
    // dimension = 1 is x, dimension = 2 is y
    if (dimension == 1) {
      return x;
    } else if (dimension == 2) {
      return y;
    }
    return null;
  }

  @Override
  public int compareTo(Position other, int dimension) {
    if (getDimension(dimension) > other.getDimension(dimension)) {
      return 1;
    } else if (getDimension(dimension) < other.getDimension(dimension)) {
      return -1;
    }
    return 0;
  }
}
