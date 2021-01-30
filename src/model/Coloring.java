package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Coloring is solution of graph coloring problem.
 */
public class Coloring {
  public int color;
  public int[] vertexColors;

  /**
   * constructor.
   *
   * @param vertex int
   */
  public Coloring(int vertex) {
    this.color = 0;
    this.vertexColors = new int[vertex];
  }

  /**
   * swapVertexColors swap colors of vertexes.
   *
   * @param v1 int
   * @param v2 int
   */
  public void swapVertexColors(final int v1, final int v2) {
    int c = vertexColors[v1];
    vertexColors[v1] = vertexColors[v2];
    vertexColors[v2] = c;
  }

  /**
   * isFeasible return the feasible solution or not.
   *
   * @param directedGraph model.DirectedGraph
   * @return boolean
   */
  public boolean isFeasible(final DirectedGraph directedGraph) {
    for (int e : directedGraph.getUndirectedGraphEdges()) {
      int t = directedGraph.tail[e];
      int h = directedGraph.head[e];

      if (vertexColors[t] == vertexColors[h]) {
        return false;
      }
    }

    return true;
  }

  /**
   * copy copy this.
   *
   * @return Coloring
   */
  public Coloring copy() {
    Coloring c = new Coloring(this.vertexColors.length);
    c.color = this.color;
    c.vertexColors = Arrays.copyOf(this.vertexColors, this.vertexColors.length);

    return c;
  }

  /**
   * updateColor calc the number of used colors.
   */
  public void updateColor() {
    ArrayList<Integer> usedColor = new ArrayList<>();
    for (int c : vertexColors) {
      if (!usedColor.contains(c)) {
        usedColor.add(c);
      }
    }

    color = usedColor.size();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("colors : %s\n", color));

    for (int v = 0; v < vertexColors.length; v++) {
      stringBuilder.append(String.format("   %3d : %2d\n", v, vertexColors[v]));
    }

    return stringBuilder.toString();
  }
}
