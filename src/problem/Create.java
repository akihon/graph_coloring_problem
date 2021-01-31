package problem;

import java.util.ArrayList;
import java.util.Random;
import model.UndirectedGraph;
import utils.exceptions.InvalidArgument;
import utils.exceptions.OccurredBug;

/**
 * Create graph coloring problem.
 */
public class Create implements CreateInterface {
  private final int vertex;  // vertex > 0
  private final Random random;

  /**
   * constructor.
   * if vertex is less than 1, throw invalid argument exception.
   *
   * @param vertex int
   * @throws InvalidArgument invalid argument exception
   */
  public Create(final int vertex) throws InvalidArgument {
    if (vertex < 1) {
      throw new InvalidArgument("vertex is more than 0");
    }

    this.vertex = vertex;
    random = new Random(System.currentTimeMillis());
  }

  @Override
  public UndirectedGraph randomNetwork(final double dense) throws InvalidArgument {
    if (dense < 0.0 || dense > 1.0) {
      throw new InvalidArgument("dense is a number between 0 and 1");
    }

    int edge = 0;
    int[] tail = new int[vertex * (vertex - 1) / 2];
    int[] head = new int[vertex * (vertex - 1) / 2];

    for (int v1 = 0; v1 < vertex - 1; v1++) {
      for (int v2 = v1 + 1; v2 < vertex; v2++) {
        if (random.nextDouble() <= dense) {
          tail[edge] = v1;
          head[edge] = v2;
          edge++;
        }
      }
    }

    return new UndirectedGraph(vertex, edge, tail, head);
  }

  @Override
  public UndirectedGraph wattsStrogatzNetwork(
      WattsStrogatzNetworkArgs args
  ) throws InvalidArgument, OccurredBug {
    args.valid(vertex);

    int mod = vertex - 1 - args.degree / 2;
    int edge = 0;
    int[] tail = new int[vertex * args.degree / 2];
    int[] head = new int[vertex * args.degree / 2];

    for (int v = 0; v < vertex; v++) {
      for (int u = 0; u < vertex; u++) {
        boolean exist = false;

        for (int e = 0; e < edge; e++) {
          if ((tail[e] == v && head[e] == u) || (tail[e] == u && head[e] == v)) {
            exist = true;
            break;
          }
        }

        if (exist) {
          continue;
        }

        int sub = Math.abs(v - u);
        if (sub % mod > 0 && sub % mod <= args.degree / 2) {
          tail[edge] = v;
          head[edge] = u;
          edge++;
        }
      }
    }

    // replace
    for (int v = 0; v < vertex; v++) {
      for (int u = v + 1; u <= v + args.degree / 2; u++) {
        if (random.nextDouble() >= args.beta) {
          continue;
        }

        int modU = u % vertex;

        ArrayList<Integer> vertexes = new ArrayList<>();
        int edgeIndex = -1;
        for (int e = 0; e < edge; e++) {
          if (tail[e] == v) {
            vertexes.add(head[e]);
          } else if (head[e] == v) {
            vertexes.add(tail[e]);
          }

          if ((tail[e] == v && head[e] == modU) || (tail[e] == modU && head[e] == v)) {
            edgeIndex = e;
          }
        }

        if (edgeIndex == -1) {
          throw new OccurredBug(String.format("no edge (%d - %d)", v, modU));
        }

        int k = random.nextInt(vertex);
        while (vertexes.contains(k) || k == v) {
          k = random.nextInt(vertex);
        }

        if (tail[edgeIndex] == v) {
          head[edgeIndex] = k;
        } else if (head[edgeIndex] == v) {
          tail[edgeIndex] = k;
        }
      }
    }

    return new UndirectedGraph(vertex, edge, tail, head);
  }

  @Override
  public String toString() {
    return String.format("vertex: %d", vertex);
  }
}
