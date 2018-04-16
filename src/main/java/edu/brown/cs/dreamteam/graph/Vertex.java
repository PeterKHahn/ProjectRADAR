package edu.brown.cs.dreamteam.graph;

import java.util.List;

/**
 * Vertex Interface.
 *
 * @param <V>
 *          generic vertex
 * @param <E>
 *          generic edge
 */
public interface Vertex<V extends Vertex<V, E>, E extends Edge<V, E>> {

  /**
   * returns List of edges.
   *
   * @return edge list
   */
  List<E> getEdges();
  
  double distanceTo(V other);

  @Override
  int hashCode();

  @Override
  boolean equals(Object obj);
}
