package edu.brown.cs.dreamteam.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.brown.cs.dreamteam.utility.Logger;

/**
 * The class represents the KD Tree data structure whose nodes store data
 * implementing the DimensionsComparable, DimensionsQueriable, and
 * DistanceCalculatable interfaces. This KD Tree is not meant to be modified
 * after initial construction.
 *
 * @param <T>
 *          The type of element that the KDTree's Nodes hold
 * @author efu2
 */
public class KDTree<T extends DimensionsComparable<T> & DimensionsQueryable<Double> & DistanceCalculatable<T>> {
  private Node<T> root;

  // Number of dimensions this KD Tree has
  private Integer dimensionNum;
  // For comparing doubles and dealing with precision issues
  private static final double EPSILON = Math.pow(10, -8);

  /**
   * Constructs a KD Tree using the given data represented in the set.
   *
   * @param elements
   *          A List of elements to add to the KD Tree
   * @param dimensionNum
   *          The number of dimensions that each instance of T has
   */
  public KDTree(List<T> elements, Integer dimensionNum) {
    if (dimensionNum < 1) {
      Logger.logError("Number of dimensions must be positive.");
      return;
    }

    this.dimensionNum = dimensionNum;
    this.root = null;

    // Build the tree with the given elements if they exist
    if (elements.size() > 0) {
      this.sortList(elements, 1);
      this.buildTree(elements);
    }
  }

  /**
   * Private method to build the tree.
   */
  private void buildTree(List<T> sorted) {
    this.addChild(sorted, null, false, 1);
  }

  /**
   * Private method to set the specified child of the given parent Node.
   */
  private void addChild(List<T> sorted, Node<T> parent, boolean isLeftChild,
      int dimension) {
    // Check that there are elements to be added to the KD Tree
    if (sorted.size() > 0) {
      // Make a Node holding the median element
      Node<T> newNode = this.makeMedianNode(sorted);
      newNode.setSplit(dimension);

      // Separate the list into the smaller elements and the larger elements by
      // the current dimension
      int nextDimension = (dimension + 1) % this.dimensionNum == 0
          ? this.dimensionNum
          : (dimension + 1) % this.dimensionNum;
      List<T> leftElements = new ArrayList<>();
      List<T> rightElements = new ArrayList<>();
      this.halveList(sorted, leftElements, rightElements, nextDimension);

      // Initialize the tree's root and build the left and right branches
      if (parent == null) {
        this.root = newNode;
        this.addChild(leftElements, this.root, true, nextDimension);
        this.addChild(rightElements, this.root, false, nextDimension);

        // Add the new Node to the tree
      } else {
        if (isLeftChild) {
          parent.setLeftChild(newNode);
          Node<T> leftChild = parent.getLeftChild();

          // Build left branch
          this.addChild(leftElements, leftChild, true, nextDimension);

          // Build right branch
          this.addChild(rightElements, leftChild, false, nextDimension);
        } else {
          parent.setRightChild(newNode);
          Node<T> rightChild = parent.getRightChild();

          // Build left branch
          this.addChild(leftElements, rightChild, true, nextDimension);

          // Build right branch
          this.addChild(rightElements, rightChild, false, nextDimension);
        }
      }
    }
  }

  /**
   * Private helper method to make a new Node with the median element in the
   * given sorted ArrayList. Removes the median element from the list.
   */
  private Node<T> makeMedianNode(List<T> sorted) {
    int numberOfElements = sorted.size();
    int medianIndex = this.getMedianIndex(numberOfElements);
    T medianElement = sorted.remove(medianIndex);
    return new Node<T>(medianElement);
  }

  /**
   * Private method to halve the given elements, assumed to be already sorted by
   * the given dimension.
   *
   * @param sorted
   *          List of elements to add to the KD Tree, sorted by the given
   *          dimension
   * @param leftElements
   *          List to build on that only contains elements less than the median
   *          at the given dimension
   * @param rightElements
   *          List to build on that only contains elements greater than the
   *          median at the given dimension.
   * @param dimension
   *          The next dimension to sort by.
   */
  private void halveList(List<T> sorted, List<T> leftElements,
      List<T> rightElements, int dimension) {
    int numberOfElements = sorted.size();
    int medianIndex = this.getMedianIndex(numberOfElements + 1);

    // Put all elements before the median into the leftElements ArrayList
    int i;
    for (i = 0; i < medianIndex; i++) {
      leftElements.add(sorted.get(i));
    }

    // Put all elements after and including the median into the rightElements
    // ArrayList
    for (; i < numberOfElements; i++) {
      rightElements.add(sorted.get(i));
    }

    // Sort the sublists by the next dimension so it is ready for constructing
    // the next level of the tree
    this.sortList(leftElements, dimension);
    this.sortList(rightElements, dimension);
  }

  /**
   * Calculate the median index given the number of elements in a list.
   */
  private int getMedianIndex(int number) {
    int median;
    if (number % 2 == 0) {
      // Choose the element immediately greater than the median as the median
      // element
      median = number / 2;
    } else {
      median = (number - 1) / 2;
    }

    return median;
  }

  /**
   * Get root node of the tree.
   * 
   * @return This KDTree's root Node
   */
  public Node<T> getRoot() {
    return this.root;
  }

  /**
   * Sort the given elements on the given dimension, from smallest to largest.
   *
   * @return A List with the sorted elements
   */
  private void sortList(List<T> elements, int dimension) {
    DimensionComparator<T> comparator = new DimensionComparator<>();
    comparator.setCompareDimension(dimension);
    Collections.sort(elements, comparator);
  }

  /**
   * Finds the k nearest neighbors to the given object in this KDTree.
   *
   * @param k
   *          The number of nearest neighbors to find.
   * @param center
   *          The object to compare distances to.
   * @param excludeCenter
   *          Whether or not the center element should be excluded in the
   *          neighbors
   * @return A List of objects of type E that are the k nearest neighbors.
   */
  public List<T> kNearestNeighbors(int k, T center, boolean excludeCenter) {
    if (k > 0) {
      // Initialize all neighbors to null
      List<T> nearest = new ArrayList<>(k);
      for (int i = 0; i < k; i++) {
        nearest.add(null);
      }

      // Initialize all distances to the max value
      List<Double> distances = new ArrayList<>(k);
      for (int i = 0; i < k; i++) {
        distances.add(Double.MAX_VALUE);
      }

      // Call the recursive helper to store correct neighbors in nearest
      this.neighborsHelper(this.root, center, nearest, distances,
          Double.MAX_VALUE, excludeCenter, true);
      return nearest;
    }

    return new ArrayList<T>();
  }

  /**
   * Finds the neighbors within a given radius to the given object.
   *
   * @param r2
   *          The squared radius around the center.
   * @param center
   *          The object to compare distances to.
   * @param excludeCenter
   *          Whether or not the center element should be excluded in the
   *          neighbors
   * @return A List of objects of type E.
   */
  public List<T> neighborsInRadius(double r2, T center, boolean excludeCenter) {
    if (r2 > 0) {
      List<T> neighbors = new ArrayList<>();
      List<Double> distances = new ArrayList<>();
      neighborsHelper(this.root, center, neighbors, distances, r2,
          excludeCenter, false);
      return neighbors;
    }

    return new ArrayList<T>();
  }

  /**
   * Recursive helper method for kNearestNeighbors to traverse the KDTree.
   *
   * @param currNode
   *          The Node to start traversing at.
   * @param center
   *          The element whose position to calculate distances from.
   * @param neighbors
   *          A sorted List of nearest neighbors found.
   * @param distances
   *          A List of distances corresponding to elements in
   *          {@code neighbors}.
   * @param distanceThreshold
   *          The distance that determines whether currNode is added to
   *          {@code neighbors}.
   * @param excludeCenter
   *          Whether or not to exclude the center in {@code neighbors}.
   * @param keepOnlyK
   *          Whether to keep only k nearest neighbors or all neighbors within
   *          {@code distanceThreshold}.
   */
  private void neighborsHelper(Node<T> currNode, T center, List<T> neighbors,
      List<Double> distances, double distanceThreshold, boolean excludeCenter,
      boolean keepOnlyK) {
    if (currNode == null) {
      return;
    }

    double distance = center.distanceTo(currNode.getElement());

    // Add currNode to neighbors if it's within the threshold
    if (distance - EPSILON < distanceThreshold) {
      // Check if center should be excluded and if currNode is center
      if (!(excludeCenter && center.equals(currNode.getElement()))) {
        this.insertNeighbor(currNode.getElement(), distance, neighbors,
            distances, keepOnlyK);
      }
    }

    // Traverse subtrees
    if (currNode.getLeftChild() != null || currNode.getRightChild() != null) {
      // Determine which subtree to traverse first
      boolean searchLeftFirst;
      int splitDimension = currNode.getSplit();
      if (center.compareTo(currNode.getElement(), splitDimension) == -1) {
        searchLeftFirst = true;
      } else {
        searchLeftFirst = false;
      }

      // Update distance threshold as necessary
      double newThreshold = distanceThreshold;
      if (keepOnlyK) {
        // Update distance threshold to only keep k nearest neighbors
        newThreshold = distances.get(distances.size() - 1);
      }

      // Search currNode's two subtrees one after another
      if (searchLeftFirst) {
        // Only traverse if elements might be closer than distanceThreshold
        if (center.getDimension(splitDimension) - distanceThreshold <= currNode
            .getElement().getDimension(splitDimension)) {
          neighborsHelper(currNode.getLeftChild(), center, neighbors, distances,
              newThreshold, excludeCenter, keepOnlyK);
        }

        // Update distance threshold as necessary
        if (keepOnlyK) {
          // Update distance threshold to only keep k nearest neighbors
          newThreshold = distances.get(distances.size() - 1);
        }

        // Only traverse if elements might be closer than distanceThreshold
        if (center.getDimension(splitDimension) + distanceThreshold > currNode
            .getElement().getDimension(splitDimension)) {
          neighborsHelper(currNode.getRightChild(), center, neighbors,
              distances, newThreshold, excludeCenter, keepOnlyK);
        }
      } else {
        // Only traverse if elements might be closer than distanceThreshold
        if (center.getDimension(splitDimension) + distanceThreshold > currNode
            .getElement().getDimension(splitDimension)) {
          neighborsHelper(currNode.getRightChild(), center, neighbors,
              distances, newThreshold, excludeCenter, keepOnlyK);
        }

        // Update distance threshold as necessary
        if (keepOnlyK) {
          // Update distance threshold to only keep k nearest neighbors
          newThreshold = distances.get(distances.size() - 1);
        }

        // Only traverse if elements might be closer than distanceThreshold
        if (center.getDimension(splitDimension) - distanceThreshold <= currNode
            .getElement().getDimension(splitDimension)) {
          neighborsHelper(currNode.getLeftChild(), center, neighbors, distances,
              newThreshold, excludeCenter, keepOnlyK);
        }
      }
    }
  }

  /**
   * Inserts an object with a distance smaller than that of the current nearest
   * neighbors to the nearest neighbors List.
   *
   * @param elementToAdd
   *          The element to add to the nearest neighbors.
   * @param distance
   *          The distance to the elementToAdd.
   * @param neighbors
   *          The List of neighbors.
   * @param distances
   *          The List of distances to each nearest neighbor.
   * @param keepOnlyK
   *          Whether to keep only k nearest neighbors.
   */
  private void insertNeighbor(T elementToAdd, double distance,
      List<T> neighbors, List<Double> distances, boolean keepOnlyK) {
    int index = 0;
    int k = distances.size();
    // Find the position of insertion
    while (index < k && distance > distances.get(index)) {
      index++;
    }

    // Shift the elements in nearest and distances by one
    T toAdd = elementToAdd;
    Double toAddDistance = distance;
    for (; index < k; index++) {
      T temp = neighbors.get(index);
      Double tempDistance = distances.get(index);

      neighbors.set(index, toAdd);
      distances.set(index, toAddDistance);

      toAdd = temp;
      toAddDistance = tempDistance;
    }

    // We want all the original elements, too (for neighbors within radius)
    if (!keepOnlyK) {
      neighbors.add(toAdd);
      distances.add(toAddDistance);
    }
  }
}
