package problem;

import java.util.Random;
import model.DirectedGraph;
import utils.exceptions.InvalidArgument;

/**
 * Create graph coloring problem.
 */
public class Create implements CreateInterface {
  private final int vertex;  // vertex > 0
  private final double dense;  // 0 <= dense <= 1 (0 is independent set, 1 is complete graph)

  /**
   * constructor.
   * If dense is less than 0 or more than 1, throw an InvalidArgument exception.
   *
   * @param vertex int
   * @param dense  double ([0, 1])
   * @throws InvalidArgument invalid argument exceptioin
   */
  public Create(final int vertex, final double dense) throws InvalidArgument {
    if (vertex < 1) {
      throw new InvalidArgument("vertex is more than 0");
    }

    if (dense < 0.0 || dense > 1.0) {
      throw new InvalidArgument("dense is a number between 0 and 1");
    }

    this.vertex = vertex;
    this.dense = dense;
  }

  @Override
  public DirectedGraph directedGraph() {
    int edge = 0;
    int[] tail = new int[vertex * (vertex - 1) / 2];
    int[] head = new int[vertex * (vertex - 1) / 2];
    Random random = new Random(System.currentTimeMillis());

    for (int v1 = 0; v1 < vertex - 1; v1++) {
      for (int v2 = v1 + 1; v2 < vertex; v2++) {
        if (random.nextDouble() <= dense) {
          tail[edge] = v1;
          head[edge] = v2;
          edge++;
        }
      }
    }

    return new DirectedGraph(vertex, edge, tail, head);
  }

  @Override
  public String toString() {
    return String.format("vertex: %d, dense: %7.5f", vertex, dense);
  }
}
