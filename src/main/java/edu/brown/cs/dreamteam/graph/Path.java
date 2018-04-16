package edu.brown.cs.dreamteam.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Path class.
 *
 * @param <V>
 *          vertex type
 * @param <E>
 *          edge type
 */
public class Path<V extends Vertex<V, E>, E extends Edge<V, E>> {

  private List<E> bridges = new ArrayList<>();
  private double totalWeight;

  /**
   * Path constructor.
   *
   * @param edges
   *          list of edges
   * @param weight
   *          weight of total edges in path
   */
  public Path(List<E> edges, double weight) {
    this.bridges = edges;
    this.totalWeight = weight;
  }

  /**
   * Helper function that copies the edge.
   *
   * @return a path.
   */
  public Path<V, E> dupe() {
    return new Path<V, E>(new ArrayList<>(this.bridges), this.totalWeight);
  }

  /**
   * Add a link to the path.
   *
   * @param newEdge
   *          new link added
   */
  public void addConnection(E newEdge) {
    bridges.add(newEdge);
    this.totalWeight += newEdge.getWeight(); // also update total weight
  }

  /**
   * Gets the total edge list.
   *
   * @return list of edges.
   */
  public List<E> getConnections() {
    return this.bridges;
  }

  /**
   * Gets the total weight.
   *
   * @return weight
   */
  public double getTotalWeight() {
    return totalWeight;
  }

  /**
   * get the best vertex.
   *
   * @return vertex of cl
   */
  public V getLastVertex() {
    return (V) bridges.get(bridges.size() - 1).getDest();
  }
  

  public double heuristicHelper(V dst) {
    return getLastVertex().distanceTo(dst);
  }

}
