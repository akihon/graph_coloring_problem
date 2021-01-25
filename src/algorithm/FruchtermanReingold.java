package algorithm;

import java.util.Arrays;
import java.util.Random;
import model.Coordinate;
import model.DirectedGraph;

/**
 * FruchtermanReingold implements Fruchterman-Reingold algorithm.
 * (C = 1, area = 1)
 */
public class FruchtermanReingold implements AlgorithmInterface<Coordinate[]> {
  private static final double ERR = 1.0e-5;

  private double temperature;
  private final double constant;
  private final DirectedGraph directedGraph;
  private final boolean[] isDegreeOne;
  private final Coordinate[] forceVectors;
  private final Coordinate[] coordinates;

  /**
   * constructor.
   *
   * @param directedGraph model.DirectedGraph
   */
  public FruchtermanReingold(final DirectedGraph directedGraph) {
    constant = Math.sqrt(1.0 / directedGraph.vertex);
    this.directedGraph = new DirectedGraph(
        directedGraph.vertex,
        directedGraph.edge,
        directedGraph.tail,
        directedGraph.head,
        directedGraph.first,
        directedGraph.adjList
    );

    isDegreeOne = new boolean[directedGraph.vertex];
    forceVectors = new Coordinate[directedGraph.vertex];
    coordinates = new Coordinate[directedGraph.vertex];
  }

  @Override
  public Coordinate[] initialize() {
    Random random = new Random(System.currentTimeMillis());

    initializeIsDegreeOne();

    for (int v = 0; v < directedGraph.vertex; v++) {
      if (isDegreeOne[v]) {
        continue;
      }

      boolean isSame = true;

      while (isSame) {
        double x = random.nextDouble();
        double y = random.nextDouble();
        Coordinate c = new Coordinate(x, y);
        isSame = false;

        for (int i = 0; i < v; i++) {
          if (!isDegreeOne[i] && coordinates[i].equal(c, ERR)) {
            isSame = true;
            break;
          }
        }

        if (!isSame) {
          coordinates[v] = c;
        }
      }
    }

    setCoordinatesOfDegreeOnePoint();
    temperature = initializeTemperature();

    return coordinates;
  }

  @Override
  public Coordinate[] algorithm(final int iteration) {
    Coordinate[] updated = new Coordinate[directedGraph.vertex];

    for (int v = 0; v < directedGraph.vertex; v++) {
      if (isDegreeOne[v]) {
        continue;
      }

      Coordinate c = coordinates[v];
      Coordinate forceVec = new Coordinate(0, 0);

      int e = directedGraph.first[v];
      while (e >= 0) {
        Coordinate headC = coordinates[directedGraph.head[e]];
        double distance = c.distance(headC);
        double force = attraction(distance) + repulsion(distance);

        Coordinate directionVec = c.calcDirectionUnitVector(headC);
        forceVec = new Coordinate(
            forceVec.getX() + directionVec.getX() * force,
            forceVec.getY() + directionVec.getY() * force
        );

        e = directedGraph.adjList[e];
      }

      forceVectors[v] = normalizeForceVector(forceVec);
      updated[v] = new Coordinate(
          coordinates[v].getX() + forceVectors[v].getX(),
          coordinates[v].getY() + forceVectors[v].getY()
      );
    }

    return updated;
  }

  @Override
  public boolean isImproved() {
    return true;
  }

  @Override
  public void update(final int iteration, final Coordinate[] result) {
    updateTemperature(iteration);
    updateCoordinates(result);
  }

  @Override
  public Coordinate[] getResult() {
    return coordinates;
  }

  private void initializeIsDegreeOne() {
    Arrays.fill(isDegreeOne, false);

    for (int v = 0; v < directedGraph.vertex; v++) {
      if (directedGraph.first[v] < 0) {
        isDegreeOne[v] = true;
      }
    }
  }

  private double initializeTemperature() {
    double maxX = 0.0;
    double minX = Double.MAX_VALUE;
    double maxY = 0.0;
    double minY = Double.MAX_VALUE;

    for (Coordinate coordinate : coordinates) {
      if (maxX < coordinate.getX()) {
        maxX = coordinate.getX();
      }
      if (minX > coordinate.getX()) {
        minX = coordinate.getX();
      }

      if (maxY < coordinate.getY()) {
        maxY = coordinate.getY();
      }
      if (minY > coordinate.getY()) {
        minY = coordinate.getY();
      }
    }

    return Math.max(maxX - minX, maxY - minY) * 0.1;
  }

  private void updateTemperature(final int iteration) {
    temperature = temperature / (iteration + 1.0);
  }

  private void updateCoordinates(Coordinate[] result) {
    System.arraycopy(result, 0, coordinates, 0, directedGraph.vertex);
    setCoordinatesOfDegreeOnePoint();
  }

  private double attraction(final double distance) {
    return distance * distance / constant;
  }

  private double repulsion(final double distance) {
    return -constant * constant / distance;
  }

  private Coordinate normalizeForceVector(final Coordinate forceVec) {
    double norm = forceVec.distanceFromOrigin();

    return new Coordinate(
        forceVec.getX() / norm * Math.min(norm, temperature),
        forceVec.getY() / norm * Math.min(norm, temperature)
    );
  }

  private void setCoordinatesOfDegreeOnePoint() {
    Random random = new Random(System.currentTimeMillis());

    for (int v = 0; v < directedGraph.vertex; v++) {
      if (!isDegreeOne[v]) {
        continue;
      }

      boolean isSame = true;

      while (isSame) {
        double x = random.nextDouble();
        double y = random.nextDouble();
        Coordinate c = new Coordinate(x, y);
        isSame = false;

        for (int i = 0; i < v; i++) {
          if (coordinates[i].equal(c, ERR)) {
            isSame = true;
            break;
          }
        }

        if (!isSame) {
          coordinates[v] = c;
        }
      }
    }
  }
}
