package edu.brown.cs.dreamteam.main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Rooms {

  private Map<String, Room> notPlayingYetRoomIDs;
  private Map<String, Room> playingRoomIDs;

  public Rooms() {
    notPlayingYetRoomIDs = new ConcurrentHashMap<>();
    playingRoomIDs = new ConcurrentHashMap<>();
  }

  public Map<String, Room> getNotPlayingYetRoomIDs() {
    return notPlayingYetRoomIDs;
  }

  public Map<String, Room> getAllRoomIDs() {
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
    while (notPlayingYetRoomIDs.keySet().contains(result)
        || playingRoomIDs.keySet().contains(result)) {
      result = generateNewRoom();
    }
    notPlayingYetRoomIDs.put(result, new Room(result));
    return result;
  }

  public void startRoom(String id) {
    if (notPlayingYetRoomIDs.keySet().contains(id)) {
      Room temp = notPlayingYetRoomIDs.get(id);
      notPlayingYetRoomIDs.remove(id);
      playingRoomIDs.put(id, temp);
    } else if (playingRoomIDs.keySet().contains(id)) {
      System.out
          .println("TIME TO DEAL WITH THE DISCONNECT RECONNECT ISSUE BINCH.");
    } else {
      throw new IllegalArgumentException(
          "This room was never initialized, or it has already started.");
    }
  }

}
