package edu.brown.cs.dreamteam.board;

import edu.brown.cs.dreamteam.graph.Edge;

public class Move implements Edge<Position, Move> {
  private final Position src;
  private final Position dest;
  private double weight;

  public Move(Position src, Position dest) {
    this.src = src;
    this.dest = dest;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Move)) {
      return false;
    }

    Move other = (Move) o;
    if (other.getSrc().equals(src) && other.getDest().equals(dest)) {
      return true;
    }

    return false;
  }

  @Override
  public Position getSrc() {
    return src;
  }

  @Override
  public Position getDest() {
    return dest;
  }

  @Override
  public double getWeight() {
    return weight;
  }

}
