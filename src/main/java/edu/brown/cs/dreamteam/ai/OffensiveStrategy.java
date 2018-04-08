package edu.brown.cs.dreamteam.ai;

public class OffensiveStrategy implements Strategy {
  private PositionPredictor pp;

  public OffensiveStrategy() {
    pp = new PositionPredictor();
  }

  @Override
  public void getNextMove(String entities) {
    // TODO: pass in enemy player's representation, check return type needed by
    // networking
    pp.nextPosition("");
  }

}
