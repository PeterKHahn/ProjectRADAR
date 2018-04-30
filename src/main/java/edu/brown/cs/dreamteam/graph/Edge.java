package edu.brown.cs.dreamteam.graph;

/**
 * interface for edge.
 *
 * @param <V>
 *          Vertex Type
 * @param <E>
 *          Edge Type
 */
public interface Edge<V extends Vertex<V, E>, E extends Edge<V, E>> {

  /**
   * Getter for source vertex.
   *
   * @return source vertex
   */
  V getSrc();

  /**
   * Getter for Destination Vertex.
   *
   * @return dest Vertex.
   */
  V getDest();

  /**
   * Gets Weight.
   *
   * @return weight.
   */
  double getWeight();

}
