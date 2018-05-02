package edu.brown.cs.dreamteam.box;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.ImmutableList;

public class BoxSet implements Reach {

  private final double reach;
  private final Collection<Box> boxes;

  public BoxSet(Collection<Box> boxes) {
    this.boxes = boxes;
    double reach = 0;
    for (Box box : boxes) {
      double tmp = box.offset().magnitude() + box.radius();
      reach = Math.max(reach, tmp);
    }
    this.reach = reach;
  }

  public BoxSet(Box box) {
    this.boxes = ImmutableList.of(box);
    reach = box.offset().magnitude() + box.radius();
  }

  public BoxSet(double radius) {
    Box box = new Box(radius);
    boxes = ImmutableList.of(box);
    reach = radius;
  }

  private static final BoxSet EMPTY_BOX = new BoxSet(Collections.emptyList());

  public static BoxSet NullBoxSet() {
    return EMPTY_BOX;
  }

  public Collection<Box> boxes() {
    return boxes;
  }

  @Override
  public double reach() {
    return reach;
  }

}
