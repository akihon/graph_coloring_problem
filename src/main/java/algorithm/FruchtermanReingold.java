package algorithm;

import java.util.Random;
import model.Coordinate;
import model.UndirectedGraph;

/**
 * FruchtermanReingold implements Fruchterman-Reingold algorithm.
 * (C = 1, area = 1)
 */
public class FruchtermanReingold implements AlgorithmInterface<Coordinate[]> {
  private static final double ERR = 1.0e-5;

  private double temperature;
  private final double constant;
  private final UndirectedGraph graph;
  private final Coordinate[] coordinates;
  private final Random random = new Random(System.currentTimeMillis());

  /**
   * constructor.
   *
   * @param graph model.UndirectedGraph
   */
  public FruchtermanReingold(final UndirectedGraph graph) {
    constant = Math.sqrt(1.0 / graph.vertex);
    this.graph = graph.copy();

    coordinates = new Coordinate[graph.vertex];
  }

  @Override
  public Coordinate[] initialize() {
    for (int v = 0; v < graph.vertex; v++) {
      boolean isSame = true;

      while (isSame) {

        double x = random.nextDouble();
        double y = random.nextDouble();
        Coordinate c = new Coordinate(x, y);
        isSame = false;

        for (int i = 0; i < v; i++) {
          if (coordinates[i].equals(c)) {
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
    Coordinate[] forceVectors = new Coordinate[graph.vertex];

    // repulsive forces
    for (int v = 0; v < graph.vertex; v++) {
      forceVectors[v] = new Coordinate(0, 0);

      for (int u = 0; u < graph.vertex; u++) {
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
    for (int e = 0; e < graph.edge; e++) {
      int v = graph.tail[e];
      int u = graph.head[e];
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
    Coordinate[] updated = new Coordinate[graph.vertex];
    for (int v = 0; v < graph.vertex; v++) {
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
    System.arraycopy(result, 0, coordinates, 0, graph.vertex);
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
