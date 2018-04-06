package edu.brown.cs.dreamteam.main;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Rooms {

  private Set<String> roomIDs;
  private Set<Socket> players;

  public Rooms() {
    roomIDs = new HashSet<>();
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
    while (roomIDs.contains(result)) {
      result = generateNewRoom();
    }
    roomIDs.add(result);
    return result;
  }

}
