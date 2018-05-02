package edu.brown.cs.dreamteam.entity;

import java.util.Collection;
import java.util.Set;

import edu.brown.cs.dreamteam.event.ClientState;
import edu.brown.cs.dreamteam.game.Chunk;
import edu.brown.cs.dreamteam.game.ChunkMap;

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
  }

  @Override
  public void tick(ChunkMap chunkMap) {

    Collection<Chunk> chunksInRange = chunkMap.chunksInRange(this);
    for (Chunk c : chunksInRange) {
      c.removeDynamic(this);
    }

    updatePosition(chunkMap); // Calls movement in dynamic entity
    inventory.tick();

    if (primaryActionFlag) { // starts attack
      inventory.getActiveWeapon().fire();
    }
    if (itemPickedFlag) {
      pickUpItem(chunkMap, chunksInRange);

    }

    // checks collision and hits them
    Set<Interactable> interactables = chunkMap
        .interactableFromChunks(chunksInRange);
    for (Interactable e : interactables) {
      if (hits(e)) {
        this.hit(e);
      }
    }

    if (health < 0) {
      this.kill();

    } else {
      Collection<Chunk> newChunks = chunkMap.chunksInRange(this);
      for (Chunk c : newChunks) {
        c.addDynamic(this);
      }
    }
  }

}
