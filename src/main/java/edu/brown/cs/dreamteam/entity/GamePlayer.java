package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.event.ClientState;

/**
 * The internal representation of a player in the Game.
 * 
 * @author peter
 *
 */
public class GamePlayer extends Playable {

  public static GamePlayer player(String sessionId, double xpos, double ypos) {
    return new GamePlayer(sessionId, xpos, ypos);
  }

  /**
   * Constructor for GamePlayer that initializes its origin position as well as
   * its id.
   * 
   * @param id
   *          the unique ID of the player
   * @param xPos
   *          the x position to start
   * @param yPos
   *          the y position to start
   */
  public GamePlayer(String id, double xPos, double yPos) {
    super(id, xPos, yPos, "HUMAN");

  }

  /**
   * Given a ClientState, updates the internal representations of the GamePlayer
   * to match the state.
   * 
   * @param state
   *          the ClientState to match
   */
  public void update(ClientState state) {
    int horzCoeff = state.retrieveHorzMultiplier();
    int vertCoeff = state.retrieveVertMultiplier();
    updatePlayer(state);
    updateDynamic(vertCoeff, horzCoeff);
  }

  /**
   * Initializes the player flags given the ClientState.
   * 
   * @param state
   *          The ClientState to match
   */
  private void updatePlayer(ClientState state) {
    itemPickedFlag = state.retrieveItemPicked();
    primaryActionFlag = state.retrievePrimaryAction();
    placeRadarFlag = state.retrieveRadar();
  }

  @Override
  public String getDrawType() {
    return "human";
  }

}
