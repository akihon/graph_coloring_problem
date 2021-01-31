package algorithm;

import java.util.ArrayList;
import java.util.Random;
import model.Coloring;
import model.UndirectedGraph;

/**
 * GraphColoring solve graph coloring problem.
 */
public class GraphColoring implements AlgorithmInterface<Coloring> {
  private static final double ALPHA = 2.0;

  private final UndirectedGraph graph;
  private Coloring coloring;
  private final Random random = new Random(System.currentTimeMillis());

  /**
   * constructor.
   *
   * @param graph model.UndirectedGraph
   */
  public GraphColoring(final UndirectedGraph graph) {
    this.graph = graph.copy();
    coloring = new Coloring(graph.vertex);
  }

  @Override
  public Coloring initialize() {
    coloring.color = graph.vertex;
    for (int v = 0; v < graph.vertex; v++) {
      coloring.vertexColors[v] = v;
    }

    return coloring;
  }

  @Override
  public void preprocessing(int iteration) {
  }

  @Override
  public Coloring algorithm(final int iteration) {
    // TODO: determine probability
    if (random.nextDouble() < 0.2) {
      return swapStrategy();
    }

    return replaceStrategy();
  }

  @Override
  public double evaluate(Coloring result) {
    // V ... #vertexes in Graph
    // SubGraph(c) ...  induced sub graph whose vertexes is color c.
    // V_c ... #vertexes of SubGraph(c)
    // E_c ... #edges of SubGraph(c)
    // penalty_c = V_c * V_c + E_c
    // penalty = sum(penalty_c) (c = 0, 1, 2, ..., result.color)
    // eval = result.color + (penalty - V) / ALPHA

    int[] parent = new int[graph.vertex];
    int[] subgraphVertexes = new int[graph.vertex];
    int[] subgraphEdges = new int[graph.vertex];

    for (int v = 0; v < parent.length; v++) {
      parent[v] = v;
      subgraphVertexes[v] = 1;
      subgraphEdges[v] = 0;
    }

    for (int e = 0; e < graph.edge; e++) {
      int t = graph.tail[e];
      int h = graph.head[e];

      if (result.vertexColors[t] != result.vertexColors[h]) {
        continue;
      }

      int ph = find(h, parent);
      int pt = find(t, parent);

      if (pt == ph) {
        subgraphEdges[pt]++;
        continue;
      }

      if (subgraphVertexes[pt] > subgraphVertexes[ph]) {
        parent[ph] = pt;
        subgraphVertexes[pt] += subgraphVertexes[ph];
        subgraphEdges[pt] += subgraphEdges[ph] + 1;
      } else {
        parent[pt] = ph;
        subgraphVertexes[ph] += subgraphVertexes[pt];
        subgraphEdges[ph] += subgraphEdges[pt] + 1;
      }
    }

    int penalty = 0;
    ArrayList<Integer> checked = new ArrayList<>();
    for (int p : parent) {
      int root = find(p, parent);

      if (checked.contains(root) || subgraphVertexes[root] == 1) {
        continue;
      }

      checked.add(root);
      penalty += subgraphVertexes[root] * subgraphVertexes[root] + subgraphEdges[root];
    }

    return result.color + (double) penalty / ALPHA;
  }

  @Override
  public void update(final Coloring result) {
    coloring = result.copy();
  }

  @Override
  public Coloring getResult() {
    return coloring;
  }

  private Coloring swapStrategy() {
    Coloring coloring = this.coloring.copy();

    int t = random.nextInt(graph.vertex);
    int h = random.nextInt(graph.vertex);

    while (t == h) {
      t = random.nextInt(graph.vertex);
      h = random.nextInt(graph.vertex);
    }

    coloring.swapVertexColors(t, h);

    return coloring;
  }

  private Coloring replaceStrategy() {
    Coloring coloring = this.coloring.copy();

    if (coloring.color == 1) {
      return coloring;
    }

    int v = random.nextInt(graph.vertex);
    int c = random.nextInt(coloring.color - 1);

    coloring.vertexColors[v] = c;
    coloring.updateColor();

    return coloring;
  }

  private int find(final int p, final int[] parent) {
    int root = p;
    while (root != parent[root]) {
      root = parent[root];
    }

    return root;
  }
}
