package edu.brown.cs.dreamteam.main;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Rooms {

    private Map<String, Room> notPlayingYetRoomIDs;
    private Map<String, Room> playingRoomIDs;

    public Rooms() {
        notPlayingYetRoomIDs = new ConcurrentHashMap<>();
        playingRoomIDs = new ConcurrentHashMap<>();
    }

    public Set<String> getNotPlayingYetRoomIDs() {
        return notPlayingYetRoomIDs.keySet();
    }

    public Boolean alreadyPlaying(String roomID) {
        return playingRoomIDs.containsKey(roomID);
    }

    public Boolean isNotPlaying(String roomID) {
        return notPlayingYetRoomIDs.containsKey(roomID);
    }

    public Boolean isRoom(String roomID) {
        return (notPlayingYetRoomIDs.containsKey(roomID)
                || playingRoomIDs.containsKey(roomID));
    }

    public Map<String, Room> getAllRoomIDs() {
        return playingRoomIDs;
    }

    public String generateNewRoom() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = Math.round((float) Math.random() * chars.length());
            if (index < chars.length()) {
                sb.append(chars.charAt(index));
            } else {
                sb.append(chars.charAt(chars.length() - 1));
            }

        }
        String result = sb.toString();
        while (isRoom(result)) {
            result = generateNewRoom();
        }
        return result;
    }

    public void startRoom(String id, Room r) {
        notPlayingYetRoomIDs.remove(id);
        playingRoomIDs.put(id, r);
    }

    public Room getNotPlayingRoom(String id) {
        return notPlayingYetRoomIDs.get(id);
    }

    public Room getPlayingRoom(String id) {
        return playingRoomIDs.get(id);
    }

    public void addNotPlayingRoom(String newRoomId, Room r) {
        notPlayingYetRoomIDs.put(newRoomId, r);
    }

    public void stopPlaying(String roomID) {
        playingRoomIDs.remove(roomID);
    }

}
