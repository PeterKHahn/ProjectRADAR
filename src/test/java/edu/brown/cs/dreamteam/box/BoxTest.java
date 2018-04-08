package edu.brown.cs.dreamteam.box;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs.dreamteam.datastructures.TwoDVector;

public class BoxTest {

  @Test
  public void testRectangleOnRectangleCollision() {

    TwoDVector zeroten = new TwoDVector(0, 10);
    TwoDVector tenzero = new TwoDVector(10, 0);

    TwoDVector inner1 = new TwoDVector(2.5, 7.5);
    TwoDVector inner2 = new TwoDVector(7.5, 2.5);

    TwoDVector upperLeft1 = new TwoDVector(-5, 15);
    TwoDVector upperLeft2 = new TwoDVector(-8, 20);

    TwoDVector upper1 = new TwoDVector(5, 15);
    TwoDVector upper2 = new TwoDVector(8, 20);

    TwoDVector upperRight1 = new TwoDVector(12, 15);
    TwoDVector upperRight2 = new TwoDVector(15, 20);

    TwoDVector left1 = new TwoDVector(-5, 5);
    TwoDVector left2 = new TwoDVector(-8, 7);

    TwoDVector right1 = new TwoDVector(15, 5);
    TwoDVector right2 = new TwoDVector(18, 7);

    TwoDVector lowerLeft1 = new TwoDVector(-5, -7);
    TwoDVector lowerLeft2 = new TwoDVector(-8, -12);

    TwoDVector lower1 = new TwoDVector(5, -7);
    TwoDVector lower2 = new TwoDVector(8, -12);

    TwoDVector lowerRight1 = new TwoDVector(11, -3);
    TwoDVector lowerRight2 = new TwoDVector(14, -1);

    RectangleBox bigBox = new RectangleBox(zeroten, tenzero);

    RectangleBox innerBox = new RectangleBox(inner1, inner2);
    RectangleBox upperLeftBox = new RectangleBox(upperLeft1, upperLeft2);
    RectangleBox upperBox = new RectangleBox(upper1, upper2);
    RectangleBox upperRightBox = new RectangleBox(upperRight1, upperRight2);
    RectangleBox leftBox = new RectangleBox(left1, left2);
    RectangleBox rightBox = new RectangleBox(right1, right2);
    RectangleBox lowerLeftBox = new RectangleBox(lowerLeft1, lowerLeft2);
    RectangleBox lowerBox = new RectangleBox(lower1, lower2);
    RectangleBox lowerRightBox = new RectangleBox(lowerRight1, lowerRight2);

    /*
     * Asserts that boxes within a given sudoku box will not collide with the
     * box, besides the trivial case where it is within the box
     */
    assertTrue(bigBox.collides(innerBox));
    assertTrue(innerBox.collides(bigBox));

    assertFalse(bigBox.collides(upperLeftBox));
    assertFalse(upperLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(upperBox));
    assertFalse(upperBox.collides(bigBox));

    assertFalse(bigBox.collides(upperRightBox));
    assertFalse(upperRightBox.collides(bigBox));

    assertFalse(bigBox.collides(leftBox));
    assertFalse(leftBox.collides(bigBox));

    assertFalse(bigBox.collides(rightBox));
    assertFalse(rightBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerLeftBox));
    assertFalse(lowerLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerBox));
    assertFalse(lowerBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerRightBox));
    assertFalse(lowerRightBox.collides(bigBox));

    /*
     * Testing UpperLeft against everything
     */

    RectangleBox upperLeftToUpperBox = new RectangleBox(upperLeft1, upper1);
    RectangleBox upperLeftToUpperRightBox = new RectangleBox(upperLeft1,
        upperRight1);
    RectangleBox upperLeftToLeftBox = new RectangleBox(upperLeft1, left1);
    RectangleBox upperLeftToInnerBox = new RectangleBox(upperLeft1, inner1);
    RectangleBox upperLeftToRightBox = new RectangleBox(upperLeft1, right1);
    RectangleBox upperLeftToLowerLeftBox = new RectangleBox(upperLeft1,
        lowerLeft1);
    RectangleBox upperLeftToLowerBox = new RectangleBox(upperLeft1, lower1);
    RectangleBox upperLeftToLowerRight = new RectangleBox(upperLeft1,
        lowerRight1);

    assertFalse(bigBox.collides(upperLeftToUpperBox));
    assertFalse(upperLeftToUpperBox.collides(bigBox));

    assertFalse(bigBox.collides(upperLeftToUpperRightBox));
    assertFalse(upperLeftToUpperRightBox.collides(bigBox));

    assertFalse(bigBox.collides(upperLeftToLeftBox));
    assertFalse(upperLeftToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperLeftToInnerBox));
    assertTrue(upperLeftToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(upperLeftToRightBox));
    assertTrue(upperLeftToRightBox.collides(bigBox));

    assertFalse(bigBox.collides(upperLeftToLowerLeftBox));
    assertFalse(upperLeftToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperLeftToLowerBox));
    assertTrue(upperLeftToLowerBox.collides(bigBox));

    assertTrue(bigBox.collides(upperLeftToLowerRight));
    assertTrue(upperLeftToLowerRight.collides(bigBox));

    /*
     * Tests upper box
     */

    RectangleBox upperToUpperLeftBox = new RectangleBox(upper1, upperLeft1);
    RectangleBox upperToUpperRightBox = new RectangleBox(upper1, upperRight1);
    RectangleBox upperToLeftBox = new RectangleBox(upper1, left1);
    RectangleBox upperToInnerBox = new RectangleBox(upper1, inner1);
    RectangleBox upperToRightBox = new RectangleBox(upper1, right1);
    RectangleBox upperToLowerLeftBox = new RectangleBox(upper1, lowerLeft1);
    RectangleBox upperToLowerBox = new RectangleBox(upper1, lower1);
    RectangleBox upperToLowerRight = new RectangleBox(upper1, lowerRight1);

    assertFalse(bigBox.collides(upperToUpperLeftBox));
    assertFalse(upperToUpperLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(upperToUpperRightBox));
    assertFalse(upperToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToLeftBox));
    assertTrue(upperToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToInnerBox));
    assertTrue(upperToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToRightBox));
    assertTrue(upperToRightBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToLowerLeftBox));
    assertTrue(upperToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToLowerBox));
    assertTrue(upperToLowerBox.collides(bigBox));

    assertTrue(bigBox.collides(upperToLowerRight));
    assertTrue(upperToLowerRight.collides(bigBox));

    /*
     * Tests upperRight boxes
     */
    RectangleBox upperRightToUpperLeftBox = new RectangleBox(upperRight1,
        upperLeft1);
    RectangleBox upperRightToUpperBox = new RectangleBox(upperRight1, upper1);
    RectangleBox upperRightToLeftBox = new RectangleBox(upperRight1, left1);
    RectangleBox upperRightToInnerBox = new RectangleBox(upperRight1, inner1);
    RectangleBox upperRightToRightBox = new RectangleBox(upperRight1, right1);
    RectangleBox upperRightToLowerLeftBox = new RectangleBox(upperRight1,
        lowerLeft1);
    RectangleBox upperRightToLowerBox = new RectangleBox(upperRight1, lower1);
    RectangleBox upperRightToLowerRight = new RectangleBox(upperRight1,
        lowerRight1);

    assertFalse(bigBox.collides(upperRightToUpperLeftBox));
    assertFalse(upperRightToUpperLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(upperRightToUpperBox));
    assertFalse(upperRightToUpperBox.collides(bigBox));

    assertTrue(bigBox.collides(upperRightToLeftBox));
    assertTrue(upperRightToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperRightToInnerBox));
    assertTrue(upperRightToInnerBox.collides(bigBox));

    assertFalse(bigBox.collides(upperRightToRightBox));
    assertFalse(upperRightToRightBox.collides(bigBox));

    assertTrue(bigBox.collides(upperRightToLowerLeftBox));
    assertTrue(upperRightToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(upperRightToLowerBox));
    assertTrue(upperRightToLowerBox.collides(bigBox));

    assertFalse(bigBox.collides(upperRightToLowerRight));
    assertFalse(upperRightToLowerRight.collides(bigBox));

    /*
     * Tests Left Box
     */

    RectangleBox leftToUpperLeftBox = new RectangleBox(left1, upperLeft1);
    RectangleBox leftToUpperBox = new RectangleBox(left1, upper1);
    RectangleBox leftToUpperRightBox = new RectangleBox(left1, upperRight1);
    RectangleBox leftToInnerBox = new RectangleBox(left1, inner1);
    RectangleBox leftToRightBox = new RectangleBox(left1, right1);
    RectangleBox leftToLowerLeftBox = new RectangleBox(left1, lowerLeft1);
    RectangleBox leftToLowerBox = new RectangleBox(left1, lower1);
    RectangleBox leftToLowerRight = new RectangleBox(left1, lowerRight1);

    assertFalse(bigBox.collides(leftToUpperLeftBox));
    assertFalse(leftToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToUpperBox));
    assertTrue(leftToUpperBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToUpperRightBox));
    assertTrue(leftToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToInnerBox));
    assertTrue(leftToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToRightBox));
    assertTrue(leftToRightBox.collides(bigBox));

    assertFalse(bigBox.collides(leftToLowerLeftBox));
    assertFalse(leftToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToLowerBox));
    assertTrue(leftToLowerBox.collides(bigBox));

    assertTrue(bigBox.collides(leftToLowerRight));
    assertTrue(leftToLowerRight.collides(bigBox));

    /*
     * Tests inner box
     */

    RectangleBox innerToUpperLeftBox = new RectangleBox(inner1, upperLeft1);
    RectangleBox innerToUpperBox = new RectangleBox(inner1, upper1);
    RectangleBox innerToUpperRightBox = new RectangleBox(inner1, upperRight1);
    RectangleBox innerToLeftBox = new RectangleBox(inner1, left1);
    RectangleBox innerToRightBox = new RectangleBox(inner1, right1);
    RectangleBox innerToLowerLeftBox = new RectangleBox(inner1, lowerLeft1);
    RectangleBox innerToLowerBox = new RectangleBox(inner1, lower1);
    RectangleBox innerToLowerRight = new RectangleBox(inner1, lowerRight1);

    assertTrue(bigBox.collides(innerToUpperLeftBox));
    assertTrue(innerToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToUpperBox));
    assertTrue(innerToUpperBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToUpperRightBox));
    assertTrue(innerToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToLeftBox));
    assertTrue(innerToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToRightBox));
    assertTrue(innerToRightBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToLowerLeftBox));
    assertTrue(innerToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToLowerBox));
    assertTrue(innerToLowerBox.collides(bigBox));

    assertTrue(bigBox.collides(innerToLowerRight));
    assertTrue(innerToLowerRight.collides(bigBox));

    /*
     * Tests RightBox
     */

    RectangleBox rightToUpperLeftBox = new RectangleBox(right1, upperLeft1);
    RectangleBox rightToUpperBox = new RectangleBox(right1, upper1);
    RectangleBox rightToUpperRightBox = new RectangleBox(right1, upperRight1);
    RectangleBox rightToLeftBox = new RectangleBox(right1, left1);
    RectangleBox rightToInnerBox = new RectangleBox(right1, inner1);
    RectangleBox rightToLowerLeftBox = new RectangleBox(right1, lowerLeft1);
    RectangleBox rightToLowerBox = new RectangleBox(right1, lower1);
    RectangleBox rightToLowerRight = new RectangleBox(right1, lowerRight1);

    assertTrue(bigBox.collides(rightToUpperLeftBox));
    assertTrue(rightToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(rightToUpperBox));
    assertTrue(rightToUpperBox.collides(bigBox));

    assertFalse(bigBox.collides(rightToUpperRightBox));
    assertFalse(rightToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(rightToLeftBox));
    assertTrue(rightToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(rightToInnerBox));
    assertTrue(rightToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(rightToLowerLeftBox));
    assertTrue(rightToLowerLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(rightToLowerBox));
    assertTrue(rightToLowerBox.collides(bigBox));

    assertFalse(bigBox.collides(rightToLowerRight));
    assertFalse(rightToLowerRight.collides(bigBox));

    /*
     * Tests Lower Left Box
     */

    RectangleBox lowerLeftToUpperLeftBox = new RectangleBox(lowerLeft1,
        upperLeft1);
    RectangleBox lowerLeftToUpperBox = new RectangleBox(lowerLeft1, upper1);
    RectangleBox lowerLeftToUpperRightBox = new RectangleBox(lowerLeft1,
        upperRight1);
    RectangleBox lowerLeftToLeftBox = new RectangleBox(lowerLeft1, left1);
    RectangleBox lowerLeftToInnerBox = new RectangleBox(lowerLeft1, inner1);
    RectangleBox lowerLeftToRightBox = new RectangleBox(lowerLeft1, right1);
    RectangleBox lowerLeftToLowerBox = new RectangleBox(lowerLeft1, lower1);
    RectangleBox lowerLeftToLowerRight = new RectangleBox(lowerLeft1,
        lowerRight1);

    assertFalse(bigBox.collides(lowerLeftToUpperLeftBox));
    assertFalse(lowerLeftToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerLeftToUpperBox));
    assertTrue(lowerLeftToUpperBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerLeftToUpperRightBox));
    assertTrue(lowerLeftToUpperRightBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerLeftToLeftBox));
    assertFalse(lowerLeftToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerLeftToInnerBox));
    assertTrue(lowerLeftToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerLeftToRightBox));
    assertTrue(lowerLeftToRightBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerLeftToLowerBox));
    assertFalse(lowerLeftToLowerBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerLeftToLowerRight));
    assertFalse(lowerLeftToLowerRight.collides(bigBox));

    /*
     * Tests lower box
     */

    RectangleBox lowerToUpperLeftBox = new RectangleBox(lower1, upperLeft1);
    RectangleBox lowerToUpperBox = new RectangleBox(lower1, upper1);
    RectangleBox lowerToUpperRightBox = new RectangleBox(lower1, upperRight1);
    RectangleBox lowerToLeftBox = new RectangleBox(lower1, left1);
    RectangleBox lowerToInnerBox = new RectangleBox(lower1, inner1);
    RectangleBox lowerToRightBox = new RectangleBox(lower1, right1);
    RectangleBox lowerToLowerLeftBox = new RectangleBox(lower1, lowerLeft1);
    RectangleBox lowerToLowerRight = new RectangleBox(lower1, lowerRight1);

    assertTrue(bigBox.collides(lowerToUpperLeftBox));
    assertTrue(lowerToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerToUpperBox));
    assertTrue(lowerToUpperBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerToUpperRightBox));
    assertTrue(lowerToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerToLeftBox));
    assertTrue(lowerToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerToInnerBox));
    assertTrue(lowerToInnerBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerToRightBox));
    assertTrue(lowerToRightBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerToLowerLeftBox));
    assertFalse(lowerToLowerLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerToLowerRight));
    assertFalse(lowerToLowerRight.collides(bigBox));

    /*
     * Tests lowerRight
     */

    RectangleBox lowerRightToUpperLeftBox = new RectangleBox(lowerRight1,
        upperLeft1);
    RectangleBox lowerRightToUpperBox = new RectangleBox(lowerRight1, upper1);
    RectangleBox lowerRightToUpperRightBox = new RectangleBox(lowerRight1,
        upperRight1);
    RectangleBox lowerRightToLeftBox = new RectangleBox(lowerRight1, left1);
    RectangleBox lowerRightToInnerBox = new RectangleBox(lowerRight1, inner1);
    RectangleBox lowerRightToRightBox = new RectangleBox(lowerRight1, right1);
    RectangleBox lowerRightToLowerLeftBox = new RectangleBox(lowerRight1,
        lowerLeft1);
    RectangleBox lowerRightToLower = new RectangleBox(lowerRight1, lower1);

    assertTrue(bigBox.collides(lowerRightToUpperLeftBox));
    assertTrue(lowerRightToUpperLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerRightToUpperBox));
    assertTrue(lowerRightToUpperBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerRightToUpperRightBox));
    assertFalse(lowerRightToUpperRightBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerRightToLeftBox));
    assertTrue(lowerRightToLeftBox.collides(bigBox));

    assertTrue(bigBox.collides(lowerRightToInnerBox));
    assertTrue(lowerRightToInnerBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerRightToRightBox));
    assertFalse(lowerRightToRightBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerRightToLowerLeftBox));
    assertFalse(lowerRightToLowerLeftBox.collides(bigBox));

    assertFalse(bigBox.collides(lowerRightToLower));
    assertFalse(lowerRightToLower.collides(bigBox));

  }

  @Test
  public void testCircleOnCircleCollision() {
    CircleBox circle1 = new CircleBox(new TwoDVector(0, 0), 10);
    CircleBox circle2 = new CircleBox(new TwoDVector(15, 0), 10);

    CircleBox circle3 = new CircleBox(new TwoDVector(0, 0), 5);

    CircleBox circle4 = new CircleBox(new TwoDVector(21, 0), 10);

    assertTrue(circle1.collides(circle2));
    assertTrue(circle2.collides(circle1));

    assertTrue(circle1.collides(circle3));
    assertTrue(circle3.collides(circle1));

    assertFalse(circle1.collides(circle4));
    assertFalse(circle4.collides(circle1));

  }

  @Test
  public void testRectangleOnCircleCollision() {
    TwoDVector zeroten = new TwoDVector(0, 10);
    TwoDVector tenzero = new TwoDVector(10, 0);

    RectangleBox bigbox = new RectangleBox(zeroten, tenzero);

    TwoDVector inner = new TwoDVector(2.5, 7.5);
    TwoDVector upperLeft = new TwoDVector(-5, 15);
    TwoDVector upper = new TwoDVector(5, 15);
    TwoDVector upperRight = new TwoDVector(12, 15);
    TwoDVector left = new TwoDVector(-5, 5);
    TwoDVector right = new TwoDVector(15, 5);
    TwoDVector lowerLeft = new TwoDVector(-5, -7);
    TwoDVector lower = new TwoDVector(5, -7);
    TwoDVector lowerRight = new TwoDVector(11, -3);

    CircleBox innerCircle = new CircleBox(inner, 5);

    assertTrue(innerCircle.collides(bigbox));
    assertTrue(bigbox.collides(innerCircle));

    CircleBox upperCircle1 = new CircleBox(upper, 3);
    CircleBox upperCircle2 = new CircleBox(upper, 6);

    assertFalse(bigbox.collides(upperCircle1));
    assertFalse(upperCircle1.collides(bigbox));
    assertTrue(bigbox.collides(upperCircle2));
    assertTrue(upperCircle2.collides(bigbox));

    CircleBox lowerCircle1 = new CircleBox(lower, 6);
    CircleBox lowerCircle2 = new CircleBox(lower, 8);

    assertFalse(bigbox.collides(lowerCircle1));
    assertFalse(lowerCircle1.collides(bigbox));
    assertTrue(bigbox.collides(lowerCircle2));
    assertTrue(lowerCircle2.collides(bigbox));

    CircleBox leftCircle1 = new CircleBox(left, 4);
    CircleBox leftCircle2 = new CircleBox(left, 6);

    assertFalse(bigbox.collides(leftCircle1));
    assertFalse(leftCircle1.collides(bigbox));
    assertTrue(bigbox.collides(leftCircle2));
    assertTrue(leftCircle2.collides(bigbox));

    CircleBox rightCircle1 = new CircleBox(right, 4);
    CircleBox rightCircle2 = new CircleBox(right, 6);

    assertFalse(bigbox.collides(rightCircle1));
    assertFalse(rightCircle1.collides(bigbox));
    assertTrue(bigbox.collides(rightCircle2));
    assertTrue(rightCircle2.collides(bigbox));

    CircleBox upperLeftCircle1 = new CircleBox(upperLeft, 3);
    CircleBox upperLeftCircle2 = new CircleBox(upperLeft, 15);

    assertFalse(bigbox.collides(upperLeftCircle1));
    assertFalse(upperLeftCircle1.collides(bigbox));
    assertTrue(bigbox.collides(upperLeftCircle2));
    assertTrue(upperLeftCircle2.collides(bigbox));

    CircleBox upperRightCircle1 = new CircleBox(upperRight, 3);
    CircleBox upperRightCircle2 = new CircleBox(upperRight, 15);

    assertFalse(bigbox.collides(upperRightCircle1));
    assertFalse(upperRightCircle1.collides(bigbox));
    assertTrue(bigbox.collides(upperRightCircle2));
    assertTrue(upperRightCircle2.collides(bigbox));

    CircleBox lowerLeftCircle1 = new CircleBox(lowerLeft, 3);
    CircleBox lowerLeftCircle2 = new CircleBox(lowerLeft, 15);

    assertFalse(bigbox.collides(lowerLeftCircle1));
    assertFalse(lowerLeftCircle1.collides(bigbox));
    assertTrue(bigbox.collides(lowerLeftCircle2));
    assertTrue(lowerLeftCircle2.collides(bigbox));

    CircleBox lowerRightCircle1 = new CircleBox(lowerRight, 3);
    CircleBox lowerRightCircle2 = new CircleBox(lowerRight, 15);

    assertFalse(bigbox.collides(lowerRightCircle1));
    assertFalse(lowerRightCircle1.collides(bigbox));
    assertTrue(bigbox.collides(lowerRightCircle2));
    assertTrue(lowerRightCircle2.collides(bigbox));

  }

  @Test
  public void testCollisionEdgeCases() {
    double epsilon = 0.0001;

    TwoDVector origin = new TwoDVector(0, 0);
    TwoDVector oneone = new TwoDVector(1, 1);
    TwoDVector oneoff = new TwoDVector(1 + epsilon, 1);
    TwoDVector twozero = new TwoDVector(2, 0);
    TwoDVector twozerooff = new TwoDVector(2 + epsilon, 0);
    TwoDVector twotwo = new TwoDVector(2, 2);

    RectangleBox rectangle1 = new RectangleBox(origin, oneone);
    RectangleBox rectangle2 = new RectangleBox(oneone, twozero);
    RectangleBox rectangle3 = new RectangleBox(oneoff, twozero);
    RectangleBox rectangle4 = new RectangleBox(oneone, twotwo);
    RectangleBox rectangle5 = new RectangleBox(oneoff, twotwo);

    assertTrue(rectangle1.collides(rectangle2));
    assertTrue(rectangle2.collides(rectangle1));

    assertFalse(rectangle1.collides(rectangle3));
    assertFalse(rectangle3.collides(rectangle1));

    assertTrue(rectangle1.collides(rectangle4));
    assertTrue(rectangle4.collides(rectangle1));

    assertFalse(rectangle1.collides(rectangle5));
    assertFalse(rectangle5.collides(rectangle1));

    CircleBox circle1 = new CircleBox(origin, 1);
    CircleBox circle2 = new CircleBox(twozero, 1);
    CircleBox circle3 = new CircleBox(twozerooff, 1);
    CircleBox circle4 = new CircleBox(origin, Math.sqrt(2) / 2);
    CircleBox circle5 = new CircleBox(oneone, Math.sqrt(2) / 2);
    CircleBox circle6 = new CircleBox(oneone, Math.sqrt(2) / 2 - epsilon);

    assertTrue(circle1.collides(circle2));
    assertTrue(circle2.collides(circle1));
    assertFalse(circle1.collides(circle3));
    assertFalse(circle3.collides(circle1));

    assertTrue(circle4.collides(circle5));
    assertTrue(circle5.collides(circle4));
    assertFalse(circle4.collides(circle6));
    assertFalse(circle6.collides(circle4));

    TwoDVector vectorAlpha = new TwoDVector(0.5, 0.5);
    RectangleBox rectangle6 = new RectangleBox(origin, vectorAlpha);
    assertTrue(rectangle6.collides(circle5));

    assertTrue(circle5.collides(rectangle6));
    assertFalse(rectangle6.collides(circle6));
    assertFalse(circle6.collides(rectangle6));

    CircleBox circle7 = new CircleBox(new TwoDVector(0.5, 2), 1);
    CircleBox circle8 = new CircleBox(new TwoDVector(0.5, 2), 1 - epsilon);

    assertTrue(circle7.collides(rectangle1));
    assertTrue(rectangle1.collides(circle7));

    assertFalse(circle8.collides(rectangle1));
    assertFalse(rectangle1.collides(circle8));

  }

}
