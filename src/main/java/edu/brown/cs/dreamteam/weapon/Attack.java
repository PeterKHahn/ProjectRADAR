package edu.brown.cs.dreamteam.weapon;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Attack implements Iterable<AttackFrame> {

  private final int duration;
  private Queue<AttackFrame> frames;

  public Attack(Queue<AttackFrame> frames) {
    int duration = 0;
    for (AttackFrame frame : frames) {
      duration += frame.duration();
    }
    this.duration = duration;
    this.frames = frames;
  }

  public int duration() {
    return duration;
  }

  @Override
  public Iterator<AttackFrame> iterator() {

    class Iter implements Iterator<AttackFrame> {
      private int frame;
      private int currentFrame;
      private AttackFrame attackFrame;

      Iter() {
        frame = 0;
        currentFrame = 0;
        attackFrame = null;
      }

      @Override
      public boolean hasNext() {
        return frame < duration;
      }

      @Override
      public AttackFrame next() {
        if (attackFrame == null || currentFrame >= attackFrame.duration()) {
          if (frames.isEmpty()) {
            throw new NoSuchElementException();
          }
          attackFrame = frames.poll();
          currentFrame = 0;

        }

        frame++;
        currentFrame++;
        return attackFrame;
      }

    }

    return new Iter();
  }

}
