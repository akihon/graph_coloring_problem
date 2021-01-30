package algorithm;

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
  private final Coordinate[] coordinates;
  private final Random random = new Random(System.currentTimeMillis());

  /**
   * constructor.
   *
   * @param directedGraph model.DirectedGraph
   */
  public FruchtermanReingold(final DirectedGraph directedGraph) {
    constant = Math.sqrt(1.0 / directedGraph.vertex);
    this.directedGraph = directedGraph.copy();

    coordinates = new Coordinate[directedGraph.vertex];
  }

  @Override
  public Coordinate[] initialize() {
    for (int v = 0; v < directedGraph.vertex; v++) {
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

    temperature = initializeTemperature();

    return coordinates;
  }

  @Override
  public void preprocessing(int iteration) {
    temperature -= temperature / (iteration + 1.0);
  }

  @Override
  public Coordinate[] algorithm(final int iteration) {
    Coordinate[] forceVectors = new Coordinate[directedGraph.vertex];

    // repulsive forces
    for (int v = 0; v < directedGraph.vertex; v++) {
      forceVectors[v] = new Coordinate(0, 0);

      for (int u = 0; u < directedGraph.vertex; u++) {
        if (v == u) {
          continue;
        }

        Coordinate delta = new Coordinate(
            coordinates[v].getX() - coordinates[u].getX(),
            coordinates[v].getY() - coordinates[u].getY()
        );

        double deltaNorm = delta.distanceFromOrigin();
        forceVectors[v].add(
            new Coordinate(
                delta.getX() / deltaNorm * repulsion(deltaNorm),
                delta.getY() / deltaNorm * repulsion(deltaNorm)
            )
        );
      }
    }

    // attraction
    for (int e : directedGraph.getUndirectedGraphEdges()) {
      int v = directedGraph.tail[e];
      int u = directedGraph.head[e];
      Coordinate delta = new Coordinate(
          coordinates[v].getX() - coordinates[u].getX(),
          coordinates[v].getY() - coordinates[u].getY()
      );

      double deltaNorm = delta.distanceFromOrigin();
      forceVectors[v].sub(
          new Coordinate(
              delta.getX() / deltaNorm * attraction(deltaNorm),
              delta.getY() / deltaNorm * attraction(deltaNorm)
          )
      );
      forceVectors[u].add(
          new Coordinate(
              delta.getX() / deltaNorm * attraction(deltaNorm),
              delta.getY() / deltaNorm * attraction(deltaNorm)
          )
      );
    }

    // update coordinates
    Coordinate[] updated = new Coordinate[directedGraph.vertex];
    for (int v = 0; v < directedGraph.vertex; v++) {
      double forceVecNorm = forceVectors[v].distanceFromOrigin();
      double x = forceVectors[v].getX() / forceVecNorm * Math.min(forceVecNorm, temperature);
      double y = forceVectors[v].getY() / forceVecNorm * Math.min(forceVecNorm, temperature);

      updated[v] = new Coordinate(
          Math.min(1.0, Math.max(0, coordinates[v].getX() + x)),
          Math.min(1.0, Math.max(0, coordinates[v].getY() + y))
      );
    }

    return updated;
  }

  @Override
  public double evaluate(Coordinate[] result) {
    return 0;
  }

  @Override
  public void update(final Coordinate[] result) {
    System.arraycopy(result, 0, coordinates, 0, directedGraph.vertex);
  }

  @Override
  public Coordinate[] getResult() {
    return coordinates;
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

  private double attraction(final double distance) {
    return distance * distance / constant;
  }

  private double repulsion(final double distance) {
    return constant * constant / distance;
  }
}
