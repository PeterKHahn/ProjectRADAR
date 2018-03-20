package edu.brown.cs.dreamteam.main;

public class Main {

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  public static void main(String[] args) {
    new Main(args).run();
  }

  private void run() {
    System.out.println("Hello World");
  }
}
