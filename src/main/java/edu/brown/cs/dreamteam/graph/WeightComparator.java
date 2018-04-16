package edu.brown.cs.dreamteam.graph;

import java.util.Comparator;

/**
 * Weight Comparator using heuristics.
 * @param <V> is a vertex type
 */
public class WeightComparator<V> implements Comparator<Path> {

  public V dst;
  
  /**
   * Constructor for Comparator.
   */
  public WeightComparator(V dst) {
    this.dst=dst;
  }

  @Override
  public int compare(Path o1, Path o2) {

    double weight1 = o1.getTotalWeight() + o1.heuristicHelper((Vertex) dst);
    
    double weight2 = o2.getTotalWeight() + o2.heuristicHelper((Vertex) dst);

    return Double.compare(weight1, weight2);
  }

}
