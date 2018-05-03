package edu.brown.cs.dreamteam.radar;

import edu.brown.cs.dreamteam.datastructures.Vector;

public class Radar {

  private double projectedDistance;
  private Vector center;

  public Radar(int numPieces, Vector center) {
    setNoise(numPieces);
    this.center = center;
  }

  private void setNoise(int numPieces) {
    double varienceFactor;
    switch (numPieces) {
      case 1:
        varienceFactor = 0.75;
        break;
      case 2:
        varienceFactor = 0.5;
        break;
      case 3:
        varienceFactor = 0.25;
        break;
      default:
        varienceFactor = 0;
        break;
    }

    projectedDistance = 0;

  }

}
