package edu.brown.cs.dreamteam.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

// heuristic

/**
 * AStarSearch implementation.
 *
 * @param <V>
 *          vertex type
 * @param <E>
 *          edge type
 */
public class AStarSearch<V extends Vertex<V, E>, E extends Edge<V, E>> {

  /**
   * Constructor.
   */
  public AStarSearch() {
  }

  /**
   * Shortest path algorithm.
   *
   * @param src
   *          vertex
   * @param dst
   *          vertex
   * @return best path.
   */
  public Path<V, E> getShortestPath(V src, V dst) {

    Comparator comp = new WeightComparator(dst);
    PriorityQueue<Path<V, E>> contestants = new PriorityQueue<Path<V, E>>(comp);

    Set<V> visited = new HashSet<>();

    // get neighbors of source
    List<E> neighbors = src.getEdges();
    // look through, create paths
    for (E e : neighbors) {
      List<E> currEdges = new ArrayList<E>();
      currEdges.add(e);
      // add edge from (neighbor's end node) to destination node
      contestants.add(new Path<V, E>(currEdges, e.getWeight()));
    }

    while (!contestants.isEmpty()) {
      Path<V, E> currBest = contestants.poll();
      V vert = currBest.getLastVertex();
      visited.add(vert);

      if (vert.equals(dst)) {
        return currBest;
      }

      for (E e : vert.getEdges()) {
        // create a new path
        if (!visited.contains(e.getDest())) {

          Path<V, E> newPath = currBest.dupe();
          newPath.addConnection(e);
          contestants.add(newPath);
        }
      }

    }

    return new Path<V, E>(Collections.emptyList(), 0);
  }

}
