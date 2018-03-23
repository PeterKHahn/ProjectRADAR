package edu.brown.cs.dreamteam.main;

public class Main {

  private Architect architect;

  private Main(String[] args) {
    init();
    run();
  }

  private void init() {
    architect = new Architect();
  }

  public static void main(String[] args) {
    new Main(args);
  }

  private void run() {
    new Thread(architect).run();
  }

}
