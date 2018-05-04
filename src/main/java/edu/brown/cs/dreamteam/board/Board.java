package edu.brown.cs.dreamteam.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.dreamteam.datastructures.Vector;
import edu.brown.cs.dreamteam.entity.Playable;
import edu.brown.cs.dreamteam.entity.StaticEntity;
import edu.brown.cs.dreamteam.game.ChunkMap;
import edu.brown.cs.dreamteam.graph.AStarSearch;
import edu.brown.cs.dreamteam.graph.Path;
import edu.brown.cs.dreamteam.item.Item;
import edu.brown.cs.dreamteam.kdtree.KDTree;
import edu.brown.cs.dreamteam.utility.DreamMath;

/**
 * Represents the game board as a graph with Positions as vertices and Moves as
 * edges.
 *
 * @author efu2
 */
public class Board {
  private final int width;
  private final int height;
  private final int totalWidth;
  private final int totalHeight;
  private final int chunkSize;
  private final ChunkMap chunks;

  private final Set<StaticEntity> obstacles;
  private Map<Item, Position> itemPositions;

  private AStarSearch<Position, Move> search;
  private KDTree<Position> tree;
  private Set<Position> positions;
  private Map<StaticEntity, List<Position>> obstacleCorners;
  private static double entitySize = Playable.SIZE;
  private static double padding = entitySize + 1;

  /**
   * Constructs the graph using the given entity information at the beginning of
   * the game.
   *
   * @param chunks
   *          A ChunkMap containing all entities in the game.
   */
  public Board(ChunkMap chunks) {
    this.chunks = chunks;
    this.width = chunks.getWidth();
    this.height = chunks.getHeight();
    this.totalWidth = chunks.getTotalWidth();
    this.totalHeight = chunks.getTotalHeight();
    this.chunkSize = chunks.getChunkSize();
    this.obstacles = chunks.getStaticEntities();
    search = new AStarSearch<>();
    constructGraph(chunks);
  }

  /**
   * Sets the size of the entity that this board is constructed for.
   */
  public static void setEntitySize(double size) {
    entitySize = size;
    padding = entitySize + 1;
  }

  public Set<Position> getPositions() {
    return positions;
  }

  public void addPosition(Position pos) {
    positions.add(pos);
  }

  private void constructGraph(ChunkMap chunks) {
    obstacleCorners = new HashMap<>();
    positions = new HashSet<>();
    itemPositions = new HashMap<>();

    // Get all obstacles from chunks
    List<StaticEntity> addedObstacles = new ArrayList<>();

    // Make positions at the edges of obstacles
    Iterator<StaticEntity> it = obstacles.iterator();
    while (it.hasNext()) {
      StaticEntity obstacle = it.next();
      positions.addAll(addObstacleCorners(addedObstacles, obstacle));
    }

    // Make positions at every chunk edge along the edges of the map
    makeMapEdgePositions();

    // Make positions at item positions
    makeItemPositions(chunks);

    tree = new KDTree<>(new ArrayList<>(positions), 2);
  }

  private void makeItemPositions(ChunkMap chunks) {
    List<Position> added = new ArrayList<>();
    Set<Item> items = ChunkMap
        .itemsFromChunks(chunks.chunksInRange(0, height - 1, 0, width - 1));
    for (Item item : items) {
      Position itemPos = new Position(item.center().x, item.center().y);
      itemPositions.put(item, itemPos);
      added.add(itemPos);
    }

    positions.addAll(added);
    for (Position pos : added) {
      addEdgesFor(pos, true);
    }
  }

  public void removePosition(Position position) {
    List<Move> moves = position.getEdges();
    for (Move move : moves) {
      Position dest = move.getDest();
      dest.removeEdge(position);
    }
    positions.remove(position);
    // TODO add removeChild method to KDTree?
    // tree.removeChild(position);
  }

  private void makeMapEdgePositions() {
    List<Position> added = new ArrayList<>();
    double maxX = width * chunkSize - padding;
    double maxY = height * chunkSize - padding;
    boolean trCorner = false;
    boolean brCorner = false;
    boolean blCorner = false;
    boolean tlCorner = false;
    // One new position at the border of every chunk on the edge of the map,
    // with a padding to prevent AI players from going past the edge of the map
    for (int i = 0; i < width; i++) {
      Position pos;
      if (!trCorner) {
        if (i * chunkSize + padding >= maxX) {
          trCorner = true;
        } else {
          // Top edge
          pos = new Position(Math.min(i * chunkSize + padding, maxX),
              height * chunkSize - padding);
          added.add(pos);
        }
      }

      if (!brCorner) {
        if ((height - i) * chunkSize - padding <= padding) {
          brCorner = true;
        } else {
          // Right edge
          pos = new Position(width * chunkSize - padding,
              Math.max((height - i) * chunkSize - padding, padding));
          added.add(pos);
        }
      }

      if (!blCorner) {
        if ((width - i) * chunkSize - padding <= padding) {
          blCorner = true;
        } else {
          // Bottom edge
          pos = new Position(
              Math.max((width - i) * chunkSize - padding, padding), padding);
          added.add(pos);
        }
      }

      if (!tlCorner) {
        if (i * chunkSize + padding >= maxY) {
          tlCorner = true;
        } else {
          // Left edge
          pos = new Position(padding, Math.min(i * chunkSize + padding, maxY));
          added.add(pos);
        }
      }
    }

    // Add edges between newly added positions
    positions.addAll(added);
    for (Position pos : added) {
      addEdgesFor(pos, true);
    }
  }

  private Collection<Position> addObstacleCorners(
      List<StaticEntity> addedObstacles, StaticEntity obstacle) {
    // Get the reach and center of the obstacle. Adds the AiPlayer size and an
    // extra 1 to the reach to ensure the player won't collide, because the
    // available Positions represent where the center of the AiPlayer can
    // traverse to
    double reach = obstacle.reach() + padding;
    Vector center = obstacle.center();

    // Make one Position at each corner, extending the circular collision box
    // to a square
    double left = center.x - reach <= padding ? padding : center.x - reach;
    double top = center.y + reach >= totalHeight - padding
        ? totalHeight - padding : center.y + reach;
    double right = center.x + reach >= totalWidth - padding
        ? totalWidth - padding : center.x + reach;
    double bottom = center.y - reach <= padding ? padding : center.y - reach;
    Position bottomLeft = new Position(left, bottom);
    Position topLeft = new Position(left, top);
    Position bottomRight = new Position(right, bottom);
    Position topRight = new Position(right, top);
    if (top == bottom) {
      bottomLeft = null;
      bottomRight = null;
    }
    if (left == right) {
      bottomLeft = null;
      topLeft = null;
    }
    List<Position> corners = new ArrayList<>();
    corners.add(bottomLeft);
    corners.add(topLeft);
    corners.add(bottomRight);
    corners.add(topRight);
    obstacleCorners.put(obstacle, corners);

    // Add edges between the edges that don't cross the obstacle
    if (bottomLeft != null) {
      bottomLeft.addEdge(topLeft);
      bottomLeft.addEdge(bottomRight);
    }
    if (topLeft != null) {
      topLeft.addEdge(bottomLeft);
      topLeft.addEdge(topRight);
    }
    if (bottomRight != null) {
      bottomRight.addEdge(bottomLeft);
      bottomRight.addEdge(topLeft);
    }
    if (topRight != null) {
      topRight.addEdge(topLeft);
      topRight.addEdge(bottomRight);
    }

    // Add edges between new corners and previous obstacles' corners in toAdd.
    for (StaticEntity otherObstacle : addedObstacles) {
      // Add edges between each of the new corners and each of the corners of
      // this obstacle as appropriate
      addEdgeBetweenCorners(addedObstacles, otherObstacle, bottomLeft);
      addEdgeBetweenCorners(addedObstacles, otherObstacle, topLeft);
      addEdgeBetweenCorners(addedObstacles, otherObstacle, bottomRight);
      addEdgeBetweenCorners(addedObstacles, otherObstacle, topRight);
    }

    return corners;
  }

  private void addEdgeBetweenCorners(List<StaticEntity> addedObstacles,
      StaticEntity obstacle, Position curr) {
    if (curr != null) {
      List<Position> corners = obstacleCorners.get(obstacle);
      // Add edges against each of the corners
      for (Position corner : corners) {
        Vector dir = curr.subtract(corner);
        boolean canAddEdge = true;
        // Check whether any other obstacle is in the way
        for (StaticEntity otherObstacle : addedObstacles) {
          if (obstacle != otherObstacle) {
            if (!obstacleNotInLine(otherObstacle, corner, dir)) {
              canAddEdge = false;
              break;
            }
          }
        }
        // No obstacles were in the way
        if (canAddEdge) {
          corner.addEdge(curr);
          curr.addEdge(corner);
        }
      }

    }
  }

  /**
   * Checks whether a specific obstacle blocks with the given line segment. Note
   * that blocking is defined to be at least 2 intersections with the obstacle's
   * 4 sides, because if an obstacle happens to intersect with the line segment
   * at a corner, the line should still be traversable.
   * 
   * @param obstacle
   *          The obstacle we are looking at.
   * @param start
   *          The start position of the line segment.
   * @param dir
   *          The direction of the line segment that also defines the end of the
   *          line segment.
   * @return True if the obstacle does not block the line segment, false
   *         otherwise.
   */
  private boolean obstacleNotInLine(StaticEntity obstacle, Position start,
      Vector dir) {
    List<Position> checkCorners = obstacleCorners.get(obstacle);
    Position bottomLeft = checkCorners.get(0);
    Position topLeft = checkCorners.get(1);
    Position bottomRight = checkCorners.get(2);
    Position topRight = checkCorners.get(3);
    Map<String, Boolean> intersects = new HashMap<>();
    intersects.put("b", false);
    intersects.put("l", false);
    intersects.put("r", false);
    intersects.put("t", false);
    intersects.put("bl", false);
    intersects.put("tl", false);
    intersects.put("br", false);
    intersects.put("tr", false);

    if (bottomLeft != null && topLeft != null && bottomRight != null) {
      // Check intersection at bottom left corner or bottom and left sides
      checkIntersection(bottomLeft, new Vector(0, topLeft.y - bottomLeft.y),
          new Vector(bottomRight.x - bottomLeft.x, 0), start, dir, intersects,
          "bl", "l", "b", "tl", "br");
    }

    if (topLeft != null && bottomLeft != null && topRight != null) {
      // Check intersection at top left corner or top and left sides
      checkIntersection(topLeft, new Vector(0, bottomLeft.y - topLeft.y),
          new Vector(topRight.x - topLeft.x, 0), start, dir, intersects, "tl",
          "l", "t", "bl", "tr");

    }

    if (topRight != null && bottomRight != null && topLeft != null) {
      // Check intersection at top right corner or top and right sides
      checkIntersection(topRight, new Vector(0, bottomRight.y - topRight.y),
          new Vector(topLeft.x - topRight.x, 0), start, dir, intersects, "tr",
          "r", "t", "br", "tl");

    }

    if (bottomRight != null && topRight != null && bottomLeft != null) {
      // Check intersection at bottom right corner or bottom and right sides
      checkIntersection(bottomRight, new Vector(0, topRight.y - bottomRight.y),
          new Vector(bottomLeft.x - bottomRight.x, 0), start, dir, intersects,
          "br", "r", "b", "tr", "bl");

    }

    int numIntersect = 0;
    Iterator<Boolean> it = intersects.values().iterator();
    while (it.hasNext()) {
      Boolean value = it.next();
      if (value) {
        numIntersect++;
      }
    }

    if (numIntersect < 2) {
      return true;
    }
    return false;
  }

  private void checkIntersection(Position corner, Vector side1, Vector side2,
      Position start, Vector dir, Map<String, Boolean> intersects,
      String cornerKey, String side1Key, String side2Key,
      String side1OtherCornerKey, String side2OtherCornerKey) {
    // Corner intersection
    Vector intersect = DreamMath.intersect(corner, side1, start, dir);
    if (intersect != null) {
      if (intersect.equals(DreamMath.intersect(corner, side2, start, dir))) {
        intersects.put(cornerKey, true);
        intersects.put(side1Key, false);
        intersects.put(side2Key, false);
      }
    }

    if (!intersects.get(cornerKey)) {
      // Intersection with side1
      if (DreamMath.doIntersect(corner, side1, start, dir)
          && !intersects.get(side1OtherCornerKey)) {
        intersects.put(side1Key, true);
      }

      // Intersection with side2
      if (DreamMath.doIntersect(corner, side2, start, dir)
          && !intersects.get(side2OtherCornerKey)) {
        intersects.put(side2Key, true);
      }
    }
  }

  /**
   * Adds a Move (edge) between this position and all other positions that are
   * not obstructed by an obstacle.
   *
   * @param pos
   *          The Position to add edges to. Assumes that this position is not
   *          inside of any obstacle.
   * @param reverse
   *          Whether or not to also add edges from other positions with the
   *          given position.
   */
  public void addEdgesFor(Position pos, boolean reverse) {
    // Add edges to all other positions have an unobstructed straight line
    // distance from the given position

    for (Position otherPos : positions) {
      boolean canAddEdge = true;
      for (StaticEntity obstacle : obstacles) {
        if (!obstacleNotInLine(obstacle, pos, otherPos.subtract(pos))) {
          canAddEdge = false;
          break;
        }
      }
      if (canAddEdge && !pos.equals(otherPos)) {
        pos.addEdge(otherPos);
        if (reverse) {
          otherPos.addEdge(pos);
        }
      }
    }
  }

  /**
   * Does A* Search between the given start and end position and gives the next
   * position that the player should go to to reach the end in the shortest
   * path. Assumes that the edges between the positions are all set up.
   *
   * @param start
   *          The starting position.
   * @param end
   *          The destination position.
   * @return A position adjacent to the starting position that starts the
   *         shortest path to the destination.
   */
  public Position getMoveTo(Position start, Position end) {
    Path<Position, Move> path = search.getShortestPath(start, end);
    Move first = path.getConnections().get(0);
    return first.getDest();
  }

  /**
   * Returns the Position on the edge of the map that is closest to the line
   * defined by pos and dir.
   * 
   * @param pos
   *          The position that the player is at.
   * @param dir
   *          The direction that the player wants to go in.
   * @return A Position on the edge of the Map that is closest to the line
   *         defined by pos and dir.
   */
  public Position getEdgePosition(Position pos, Vector dir) {
    System.out.println(dir.toString());
    double xFactor = (totalWidth - 2 * padding - pos.x) / Math.abs(dir.x);
    double yFactor = (totalHeight - 2 * padding - pos.y) / Math.abs(dir.y);
    double factor = xFactor > yFactor ? yFactor : xFactor;

    // Use the Position that is as close to either border as the center of
    // the position we want to search for.
    Position center = new Position(pos.x + factor * dir.x,
        pos.y + factor * dir.y);

    // Get the nearest neighbor to the center position
    Position edgePos = tree.kNearestNeighbors(1, center, false).get(0);

    // System.out.println("Position closest to " + pos.toString()
    // + " going in dir " + dir.toString() + " is " + edgePos.toString());
    return edgePos;
  }

}
