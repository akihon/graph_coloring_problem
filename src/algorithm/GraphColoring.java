package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import model.Coloring;
import model.DirectedGraph;

/**
 * GraphColoring solve graph coloring problem.
 */
public class GraphColoring implements AlgorithmInterface<Coloring> {
  private static final double ALPHA = 2.0;

  private final DirectedGraph directedGraph;
  private Coloring coloring;
  private final Random random = new Random(System.currentTimeMillis());

  /**
   * constructor.
   *
   * @param directedGraph model.DirectedGraph
   */
  public GraphColoring(final DirectedGraph directedGraph) {
    this.directedGraph = directedGraph.copy();
    coloring = new Coloring(directedGraph.vertex);
  }

  @Override
  public Coloring initialize() {
    coloring.color = directedGraph.vertex;
    for (int v = 0; v < directedGraph.vertex; v++) {
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
    int penalty = 0;
    ArrayList<Integer> checked = new ArrayList<>();

    for (int v = 0; v < directedGraph.vertex; v++) {
      if (checked.contains(v)) {
        continue;
      }

      int color = result.vertexColors[v];
      int subGraphVertexes = 0;
      int subGraphEdges = 0;
      LinkedList<Integer> ll = new LinkedList<>(Collections.singletonList(v));

      while (ll.size() > 0) {
        int vv = ll.poll();
        int e = directedGraph.first[vv];
        checked.add(vv);
        subGraphVertexes++;

        while (e > -1) {
          int headVertex = directedGraph.head[e];

          if (result.vertexColors[headVertex] == color) {
            if (!checked.contains(headVertex) && !ll.contains(headVertex)) {
              ll.addLast(directedGraph.head[e]);
              subGraphEdges++;
            } else if (ll.contains(headVertex)) {
              subGraphEdges++;
            }
          }

          e = directedGraph.adjList[e];
        }
      }

      penalty += subGraphVertexes * subGraphVertexes + subGraphEdges;
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

    int t = random.nextInt(directedGraph.vertex);
    int h = random.nextInt(directedGraph.vertex);

    while (t == h) {
      t = random.nextInt(directedGraph.vertex);
      h = random.nextInt(directedGraph.vertex);
    }

    coloring.swapVertexColors(t, h);

    return coloring;
  }

  private Coloring replaceStrategy() {
    Coloring coloring = this.coloring.copy();

    if (coloring.color == 1) {
      return coloring;
    }

    int v = random.nextInt(directedGraph.vertex);
    int c = random.nextInt(coloring.color - 1);

    coloring.vertexColors[v] = c;
    coloring.updateColor();

    return coloring;
  }
}
