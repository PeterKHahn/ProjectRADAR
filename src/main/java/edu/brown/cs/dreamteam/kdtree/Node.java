package edu.brown.cs.dreamteam.kdtree;

/**
 * Represents a Node of a KD Tree, where the element that the Node holds must
 * implement the DimensionsComparable interface.
 *
 * @param <T> The type of object that a Node holds
 * @author efu2
 */
class Node<T extends DimensionsComparable<T>> {
  private T element;
  private int splitDimension;
  private Node<T> leftChild;
  private Node<T> rightChild;

  /**
   * Initializes the Node with the given element.
   */
  Node(T element) {
    this.element = element;
    this.leftChild = null;
    this.rightChild = null;
  }

  /**
   * Set the dimension that the tree is being split at at this node's insertion.
   * E.g. Root node's dimension is 1, both of its children's dimensions are 2.
   */
  public void setSplit(int dimension) {
    this.splitDimension = dimension;
  }

  /**
   * Get the dimension of this node.
   */
  public int getSplit() {
    return this.splitDimension;
  }

  /**
   * Set the left child of this node.
   */
  public void setLeftChild(Node<T> leftChild) {
    this.leftChild = leftChild;
  }

  /**
   * Get the left child of this node.
   */
  public Node<T> getLeftChild() {
    return this.leftChild;
  }

  /**
   * Set the right child of this node.
   */
  public void setRightChild(Node<T> rightChild) {
    this.rightChild = rightChild;
  }

  /**
   * Get the right child of this node.
   */
  public Node<T> getRightChild() {
    return this.rightChild;
  }

  /**
   * Get the element held at this node.
   */
  public T getElement() {
    return this.element;
  }

  @Override
  public String toString() {
    boolean hasLeft = (this.leftChild == null) ? false : true;
    boolean hasRight = (this.rightChild == null) ? false : true;
    return "Element: " + this.element.toString() + "\nSplit dimension: "
        + Integer.toString(this.splitDimension) + "\nHas left child: "
        + Boolean.toString(hasLeft) + "\nHas right child: "
        + Boolean.toString(hasRight);
  }
}
