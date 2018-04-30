package edu.brown.cs.dreamteam.main;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Rooms {

  private Set<String> notPlayingYetRoomIDs;
  private Set<Socket> players;
  private Set<String> playingRoomIDs;

  public Rooms() {
    notPlayingYetRoomIDs = new HashSet<>();
    playingRoomIDs = new HashSet<>();
  }

  public Set<String> getNotPlayingYetRoomIDs() {
    return notPlayingYetRoomIDs;
  }

  public Set<String> getAllRoomIDs() {
    return playingRoomIDs;
  }

  public String generateNewRoom() {
    String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    String result = "";
    for (int i = 0; i < 6; i++) {
      int index = Math.round((float) Math.random() * chars.length());
      if (index < chars.length()) {
        result += chars.charAt(index);
      } else {
        result += chars.charAt(chars.length() - 1);
      }

    }
    while (notPlayingYetRoomIDs.contains(result)) {
      result = generateNewRoom();
    }
    notPlayingYetRoomIDs.add(result);
    return result;
  }

  public void startRoom(String id) {
    if (notPlayingYetRoomIDs.contains(id)) {
      notPlayingYetRoomIDs.remove(id);
      playingRoomIDs.add(id);
    } else {
      throw new IllegalArgumentException("This room was never initialized!");
    }
  }

}
