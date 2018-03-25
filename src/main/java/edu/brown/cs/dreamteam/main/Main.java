package edu.brown.cs.dreamteam.main;

public class Main {

  private DebugMode debugMode;

  private Main(String[] args) {
    init();
    run();
  }

  private void init() {
    debugMode = DebugMode.GAME_SYNC;
  }

  public static void main(String[] args) {
    new Main(args);
  }

  private void run() {
    new Thread(debugMode.architect()).run();
  }

}
