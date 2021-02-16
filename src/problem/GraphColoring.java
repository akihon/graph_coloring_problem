package problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import model.UndirectedGraph;
import utils.FileSystem;
import utils.exceptions.InvalidArgument;
import utils.exceptions.NotFound;
import utils.exceptions.OccurredBug;

/**
 * GraphColoring graph coloring problem.
 */
public class GraphColoring implements GraphColoringInterface {
  private final Random random;
  private final boolean verbose;

  /**
   * constructor.
   */
  public GraphColoring(final boolean verbose) {
    random = new Random(System.currentTimeMillis());
    this.verbose = verbose;
  }

  @Override
  public UndirectedGraph randomNetwork(
      final int vertex, final double dense
  ) throws InvalidArgument {
    if (vertex < 1) {
      throw new InvalidArgument("vertex is more than 0");
    }

    if (dense < 0.0 || dense > 1.0) {
      throw new InvalidArgument(
          String.format("%s : dense is a number between 0 and 1", GraphColoring.class.getName())
      );
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

    System.out.printf("created undirected graph (vertex : %d, edge : %d)\n", vertex, edge);
    return new UndirectedGraph(vertex, edge, tail, head);
  }

  @Override
  public UndirectedGraph wattsStrogatzNetwork(
      final WattsStrogatzNetworkArgs args
  ) throws InvalidArgument, OccurredBug {
    String argsMsg = args.valid();
    if (!argsMsg.equals("")) {
      throw new InvalidArgument(
          String.format("%s : %s", GraphColoring.class.getName(), argsMsg)
      );
    }

    int mod = args.vertex - 1 - args.degree / 2;
    int edge = 0;
    int[] tail = new int[args.vertex * args.degree / 2];
    int[] head = new int[args.vertex * args.degree / 2];

    for (int v = 0; v < args.vertex; v++) {
      for (int u = 0; u < args.vertex; u++) {
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
    for (int v = 0; v < args.vertex; v++) {
      for (int u = v + 1; u <= v + args.degree / 2; u++) {
        if (random.nextDouble() >= args.beta) {
          continue;
        }

        int modU = u % args.vertex;

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
          String msg = String.format(
              "%s : no edge (%d - %d)", GraphColoring.class.getName(), v, modU
          );
          throw new OccurredBug(msg);
        }

        int k = random.nextInt(args.vertex);
        while (vertexes.contains(k) || k == v) {
          k = random.nextInt(args.vertex);
        }

        if (tail[edgeIndex] == v) {
          head[edgeIndex] = k;
        } else if (head[edgeIndex] == v) {
          tail[edgeIndex] = k;
        }
      }
    }

    System.out.printf("created undirected graph (vertex : %d, edge : %d)", args.vertex, edge);
    return new UndirectedGraph(args.vertex, edge, tail, head);
  }

  @Override
  public UndirectedGraph readFile(final String fileName) throws NotFound, OccurredBug {
    String fullPath = FileSystem.pathJoin("./", "data", "graph_coloring", fileName);
    int vertex = 0;
    int edge = 0;
    int index = 0;
    int[] tail = null;
    int[] head = null;

    try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
      String line;

      while ((line = br.readLine()) != null) {
        String[] splitLine = line.split(" ");
        if (splitLine.length == 0) {
          continue;
        }

        switch (splitLine[0]) {
          case "p":
            vertex = Integer.parseInt(splitLine[2]);
            edge = Integer.parseInt(splitLine[3]);
            tail = new int[edge];
            head = new int[edge];
            break;
          case "e":
            if (tail == null) {
              String message = String.format("unexpected file format (file = %s)", fullPath);
              throw new OccurredBug(message);
            }

            tail[index] = Integer.parseInt(splitLine[1]) - 1;
            head[index] = Integer.parseInt(splitLine[2]) - 1;
            index++;
            break;
          default:
        }
      }
    } catch (FileNotFoundException e) {
      String message = String.format("%s (file = %s)", e.toString(), fullPath);
      throw new NotFound(message);
    } catch (IOException | NumberFormatException e) {
      String message = String.format("%s (file = %s)", e.toString(), fullPath);
      throw new OccurredBug(message);
    }

    System.out.printf("created undirected graph (vertex : %d, edge : %d)", vertex, edge);
    return new UndirectedGraph(vertex, edge, tail, head);
  }
}
