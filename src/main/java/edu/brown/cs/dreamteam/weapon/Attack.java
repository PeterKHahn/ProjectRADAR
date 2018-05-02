package edu.brown.cs.dreamteam.weapon;

import java.util.LinkedList;
import java.util.Queue;

import edu.brown.cs.dreamteam.box.BoxSet;
import edu.brown.cs.dreamteam.box.HitBoxed;
import edu.brown.cs.dreamteam.utility.Logger;

public class Attack implements HitBoxed {

  private final int duration;
  private Queue<AttackFrame> frames;

  private int frame; // the frame of the move

  private AttackFrame currentAttackFrame;
  private int currentFrame; // the frame of the current AttackFrame

  private boolean attacking;

  public Attack(Queue<AttackFrame> frames) {
    int duration = 0;
    for (AttackFrame frame : frames) {
      duration += frame.duration();
    }
    this.duration = duration;
    this.frames = frames;
    init();
  }

  private void init() {
    attacking = false;
    currentAttackFrame = new InactiveAttackFrame();
    frame = 0; // attack Frames start at 1
    currentFrame = 0;
  }

  public int duration() {
    return duration;
  }

  public void tick() {
    if (attacking) {
      Logger.logDebug("Frame: " + frame);
      Logger.logDebug("Damage: " + currentAttackFrame.baseDamage());

      currentAttackFrame = next();

      frame++;
      currentFrame++;
    }
  }

  public void attack() {
    if (canStartAttack()) {
      attacking = true;
    }

  }

  public boolean canStartAttack() {
    return !attacking;
  }

  private AttackFrame next() {
    if (currentAttackFrame == null
        || currentFrame >= currentAttackFrame.duration()) {
      if (frames.isEmpty()) {
        attacking = false;
        frame = 0;
        currentFrame = 0;
        return new InactiveAttackFrame();
      }
      currentAttackFrame = frames.poll();
      currentFrame = 0;

    }

    return currentAttackFrame;
  }

  @Override
  public BoxSet hitBox() {
    return currentAttackFrame.hitBox();
  }

  @Override
  public double baseDamage() {
    return currentAttackFrame.baseDamage();
  }

  public static AttackBuilder create() {
    return new AttackBuilder();
  }

  public static class AttackBuilder {

    private Queue<AttackFrame> attackFrameQueue;

    public AttackBuilder() {
      attackFrameQueue = new LinkedList<AttackFrame>();
    }

    public AttackBuilder addInactive(int numFrames) {
      attackFrameQueue.add(new InactiveAttackFrame(numFrames));
      return this;
    }

    public AttackBuilder addCircle(int duration, double damamge,
        double radius) {
      attackFrameQueue
          .add(new ActiveAttackFrame(duration, damamge, new BoxSet(radius)));
      return this;
    }

    public AttackBuilder addAttackFrame(AttackFrame attackFrame) {
      attackFrameQueue.add(attackFrame);
      return this;
    }

    public Attack build() {
      return new Attack(attackFrameQueue);
    }
  }

}
