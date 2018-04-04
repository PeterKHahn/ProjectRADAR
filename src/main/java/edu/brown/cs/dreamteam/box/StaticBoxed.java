package edu.brown.cs.dreamteam.box;

/**
 * A static box is defined with having immutability, although this property is
 * not enforced in the interface.
 * 
 * @author peter
 *
 */
public interface StaticBoxed extends Boxed {

  /**
   * Returns an immutable RectangleBox, that represents the object's bounding
   * box.
   *
   * @return
   */
  public RectangleBox getBox();

}
